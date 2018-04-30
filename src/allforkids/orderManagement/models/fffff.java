/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.models;

//import allforkids.orderManagement.controllers.OrderView;
import allforkids.orderManagement.models.Order.OrderStatus;
import allforkids.store.models.Product;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import allforkids.userManagement.models.User;

/**
 *
 * @author KHOUBEIB
 */
public class fffff {

    /**
     * @param args the command line arguments
     * @throws dopsie.exceptions.ModelException
     */
    public static void main(String[] args) throws ModelException, UnsupportedDataTypeException {

        System.setProperty("host", "pma.allforkids.ml");
        System.setProperty("port", "3306");
        System.setProperty("database", "from_scratch");
        System.setProperty("user", "wassim");
        System.setProperty("password", "Lpd*de7W");
        System.setProperty("uploads_folder", "D:/Esprit/Projets/Java Web/AllForKids(kbach)/uploads/");

          Order o = Model.find(Order.class, 10);
//        o.setOrderStatus(OrderStatus.VERIFICATIONREQUIRED);
//        o.save();
//        System.out.println(o.getOrderStatus());
//        System.out.println(o.getOrderTotalVat());
//        System.out.println(o.getOrderTotalWithoutVAT());
//        System.out.println(o.getOrderTotalWithVAT());
//        System.out.println(o.customer());
//        List<Order.OrderStatus> lisFromEnum = Arrays.asList(Order.OrderStatus.values());

//        ShippingMethod m = new ShippingMethod();
//        m.setAttr("name", "Chrono Post");
//        m.setAttr("extra_fee", 35.5);
//        m.save();


//        System.out.println(Arrays.toString(OrderStatus.values()));
//         System.out.println(o.getOrderStatusIndex());
//         System.out.println(o.getOrderStatusName());
//         o.setOrderStatusByIndex(10);
//         System.out.println(o.getOrderStatusName());
//         o.setOrderStatusByName("DISPUTED");
//         System.out.println(o.getOrderStatusName());

//        System.out.println("================================================");
//        System.out.println("payment sum : " + o.getTotalPayment());
//        System.out.println("total ttc : " + o.getOrderTotalWithVAT());
//        System.out.println("reste à payer :" + (o.getOrderTotalWithVAT() - o.getTotalPayment()));
//        o.paymentAction(469.76, Payment.PaymentMethod.CREDITPOINT.name());
//        System.out.println("reste à payer :" + (o.getOrderTotalWithVAT() - o.getTotalPayment()));

//        System.out.println(o.getTotalPayment());
//
//        ObservableList<OrderView> orders = FXCollections.observableArrayList();
//
//        ArrayList<Order> allOrderList = null;
//        try {
//            allOrderList = Model.fetch(Order.class).all().execute();
//        } catch (ModelException | UnsupportedDataTypeException ex) {
//            Logger.getLogger(TreeOrderController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//        for (Order d : allOrderList) {
//
//            try {
//                orders.add(new OrderView(
//                        (int) d.getAttr("id"),
//                        (String) d.getAttr("reference"),
//                        d.customer().getFullName(),
//                        d.getOrderStatus(),
//                        d.getShippingMethod(),
//                        //        (Date)d.getAttr("creation_date"),
//                        (String) df.format((java.sql.Date) d.getAttr("creation_date")),
//                        d.getOrderTotalWithVAT().toString()
//                ));
//                //       System.out.println("------------------------------------->" + orders);  (String) df.format(
//            } catch (ModelException ex) {
//                Logger.getLogger(TreeOrderController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        orders.stream().forEach(p -> System.out.println(p.getAmount() + "" + p.getCreationDate()));
//

 //       System.out.println(randomStringGenerator.generateString());


//        User u = Model.find(User.class, 1);
//        System.out.println(u.getUserShoppingCart());
//        
//        Product p = Model.find(Product.class, 1);
//        Product p1 = Model.find(Product.class, 2);
//        Product p2 = Model.find(Product.class, 3);
//        Product p3 = Model.find(Product.class, 6);
        
      //  u.getUserShoppingCart().addItemToShoppingCart(p3);
        System.out.println(Order.OrderStatus.valueOf("CANCELLED").ordinal());
        String s = Order.OrderStatus.valueOf("COMPLETED").statusName();
        for (OrderStatus ww : OrderStatus.values()){
            if  (ww.statusName().equals(s)){
                System.out.println(ww.ordinal());
            }
            
        }
        
//      String link = (String) u.getUserShoppingCart().lineItems().get(0).product().getAttr("image_link");
//        System.out.println(link);
        //ImageView v = new ImageView("D:/Esprit/Projets/Java Web/AllForKids(kbach)/src/allforkids/orderManagement/views/image/" + link + ".jpg");
        // ImageView v = new ImageView("D:/" + link + ".jpg");
//        System.out.println(o.isShoppingCart());
//        System.out.println(o);
//        System.out.println(o.lineItems());
//        System.out.println("-------------------------------------is the order a shopping cart "+o.isShoppingCart());
//        ArrayList<LineItem> a = new ArrayList<>();
//        for (LineItem l : o.lineItems())
//        {
//            System.out.println(l.product());
//        }
//        
//        ShoppingCart cart = new ShoppingCart();
//        
//        Product p = new Product();
//        p.setAttr("product_name", "Biscuit");
//        p.setAttr("product_price", 10);
//        p.setAttr("image_link", "biscuit");
//        p.save();
//        User user1 = Model.find(User.class, 2);
//        user1.orders().get(0).
//        
//        Product bonbon = Model.find(Product.class, 1);
//        Product chocolat = Model.find(Product.class, 2);
//        Product biscuit = Model.find(Product.class, 3);
//        Product jouet = Model.find(Product.class, 4);
//        Product poupe = Model.find(Product.class, 5);
//        LineItem l = new LineItem();
//        l.setAttr("product_id", 3);
//        l.setAttr("order_id", 2);
//        l.setAttr("quantity", 30);
//        l.save();
//        cart.addItem(p1, o);
//        User u  = Model.find(User.class, 1);
//        System.out.println(u.orders());
//        User u = Model.find(User.class, 2);
//        System.out.println("======= Begin =======");
//        System.out.println("======= Lines items of the shopping cart of user u =======");
//        System.out.println(u.getUserShoppingCart().lineItems());
//        System.out.println("END ======= Lines items of the shopping cart of user u =======");
        //      u.getUserShoppingCart().addItemToShoppingCart(bonbon);
//        u.getUserShoppingCart().addItemToShoppingCart(chocolat);
//        u.getUserShoppingCart().addItemToShoppingCart(jouet);
//        u.getUserShoppingCart().addItemToShoppingCart(biscuit);
//  System.out.println(u.getUserShoppingCart().lineItems());
//        LineItem l = Model.find(LineItem.class, 17);
        //    System.out.println(l.getQuantity());
//
//        System.out.println("Number of line Item in the user shopping CART " + u.getUserShoppingCart().getNumberOfLineItem());
//        System.out.println("Number of ALL ITEM in the user Shopping CART " + u.getUserShoppingCart().getNumberOfAllItems());
//
//        System.out.println("bonbon price " + bonbon.getPrice() + "-- vat percent " + bonbon.vatRate() + " --- VAT " + bonbon.vat() + " TTc" + bonbon.getVatPrice());
//        ArrayList<LineItem> allLineItem = Model.fetch(LineItem.class).all().execute();
//
//        for (LineItem i : allLineItem) {
//            i.updateCalculation();
//        }
//LineItem li = Model.find(LineItem.class, 2);
////li.calculateTotal
//System.out.println(li.getAllAttributes());
//li.setAttr("sub_total", 5000);
//System.out.println(li.getAllAttributes());
//Payment p = new Payment();
//
//        p.setAttr("status", p.toString());
//        p.save();
    }

    /*   static class OrderView extends RecursiveTreeObject<OrderView> {

        StringProperty reference;
        StringProperty customer;
        StringProperty status;
        StringProperty shippingMethod;
        StringProperty creationDate;
        StringProperty amount;

        public OrderView(int id, String reference, String customer, String status, String shippingMethod, String creationDate, String amount) {
            this.reference = new SimpleStringProperty(reference);
            this.customer = new SimpleStringProperty(customer);
            this.status = new SimpleStringProperty(status);
            this.shippingMethod = new SimpleStringProperty(shippingMethod);
            this.creationDate = new SimpleStringProperty(creationDate);
            this.amount = new SimpleStringProperty(amount);

        }

        @Override
        public String toString() {
            return "OrderView{" + "reference=" + reference + ", customer=" + customer + ", status=" + status + ", shippingMethod=" + shippingMethod + ", creationDate=" + creationDate + ", amount=" + amount + '}';
        }

        public StringProperty getCustomer() {
            return customer;
        }

        public StringProperty getStatus() {
            return status;
        }

        public StringProperty getShippingMethod() {
            return shippingMethod;
        }

        public StringProperty getCreationDate() {
            return creationDate;
        }

        public StringProperty getAmount() {
            return amount;
        }
    }
    
     */
}
