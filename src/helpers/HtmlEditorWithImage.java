/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import netscape.javascript.JSException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Wassim
 */
public class HtmlEditorWithImage extends HTMLEditor {

    public HtmlEditorWithImage(Window stage, double height, double width) {
        Node toolNode = this.lookup(".top-toolbar");
        Node webNode = this.lookup(".web-view");
        if (toolNode instanceof ToolBar && webNode instanceof WebView) {
            ToolBar bar = (ToolBar) toolNode;

            WebView webView = (WebView) webNode;
            double tooltBarHeight = 135;
            webView.setMinHeight(height - tooltBarHeight);
            webView.setPrefHeight(height - tooltBarHeight);
            webView.setMinHeight(height - tooltBarHeight);
            webView.setMinWidth(width);
            webView.setPrefWidth(width);
            webView.setMinWidth(width);
            WebEngine engine = webView.getEngine();

            Button btnCaretAddImage = new Button("add an image");
            btnCaretAddImage.setMinSize(100.0, 24.0);
            btnCaretAddImage.setMaxSize(100.0, 24.0);

            bar.getItems().addAll(btnCaretAddImage);
            btnCaretAddImage.setOnAction((ActionEvent event) -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select an image");

                fileChooser.setInitialDirectory(
                        new File(System.getProperty("user.home"))
                );
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("PNG", "*.png")
                );
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    try {
                        String fileExtension = getFileExtension(file);
                        BufferedImage bufferedImage = ImageIO.read(file);
                        String base64String = encodeToString(bufferedImage, fileExtension);

                        String imageFormat = fileExtension;
                        String img = createEmbedImg(base64String, imageFormat);
                        String jsCodeInsertHtml = "function insertHtmlAtCursor(html) {\n"
                                + "    var range, node;\n"
                                + "    if (window.getSelection && window.getSelection().getRangeAt) {\n"
                                + "        range = window.getSelection().getRangeAt(0);\n"
                                + "        node = range.createContextualFragment(html);\n"
                                + "        range.insertNode(node);\n"
                                + "    } else if (document.selection && document.selection.createRange) {\n"
                                + "        document.selection.createRange().pasteHTML(html);\n"
                                + "    }\n"
                                + "}insertHtmlAtCursor('####html####')";
                        try {
                            engine.executeScript(jsCodeInsertHtml.
                                    replace("####html####",
                                            escapeJavaStyleString(img, true, true)));
                        } catch (JSException e) {
                            System.out.println(e.getMessage());
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            });
        }
    }

    //int to string
    private static String hex(int i) {
        return Integer.toHexString(i);
    }

    //a method to convert to a javas/js style string 
    //https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/src-html/org/apache/commons/lang/StringEscapeUtils.html
    private static String escapeJavaStyleString(String str,
            boolean escapeSingleQuote, boolean escapeForwardSlash) {
        StringBuilder out = new StringBuilder("");
        if (str == null) {
            return null;
        }
        int sz;
        sz = str.length();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);

            // handle unicode
            if (ch > 0xfff) {
                out.append("\\u").append(hex(ch));
            } else if (ch > 0xff) {
                out.append("\\u0").append(hex(ch));
            } else if (ch > 0x7f) {
                out.append("\\u00").append(hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.append('\\');
                        out.append('b');
                        break;
                    case '\n':
                        out.append('\\');
                        out.append('n');
                        break;
                    case '\t':
                        out.append('\\');
                        out.append('t');
                        break;
                    case '\f':
                        out.append('\\');
                        out.append('f');
                        break;
                    case '\r':
                        out.append('\\');
                        out.append('r');
                        break;
                    default:
                        if (ch > 0xf) {
                            out.append("\\u00").append(hex(ch));
                        } else {
                            out.append("\\u000").append(hex(ch));
                        }
                        break;
                }
            } else {
                switch (ch) {
                    case '\'':
                        if (escapeSingleQuote) {
                            out.append('\\');
                        }
                        out.append('\'');
                        break;
                    case '"':
                        out.append('\\');
                        out.append('"');
                        break;
                    case '\\':
                        out.append('\\');
                        out.append('\\');
                        break;
                    case '/':
                        if (escapeForwardSlash) {
                            out.append('\\');
                        }
                        out.append('/');
                        break;
                    default:
                        out.append(ch);
                        break;
                }
            }
        }
        return out.toString();
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    private static String createEmbedImg(String base64String, String imageFormat) {

        String img
                = "<img style=\"max-width : 700px\"alt=\"Embedded Image\" src=\"data:image/"
                + imageFormat
                + ";base64,"
                + base64String
                + "\" />";

        return img;
    }

    private static String readFile(File file) {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return stringBuffer.toString();
    }

    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return imageString;
    }
}
