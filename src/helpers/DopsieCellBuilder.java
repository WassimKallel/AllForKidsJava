
package helpers;

import dopsie.core.Model;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import java.util.function.Function;
/**
 *
 * @author Wassim
 * @param <T>
 * @param <E>
 */
public class DopsieCellBuilder<T extends CellDataFeatures<Model, String>, E extends ObservableValue<String>> implements Callback<T, E> {
    
    private final Function<Model, String> func;
    
    public DopsieCellBuilder(Function<Model, String> func) {
        this.func = func;
    }

    @Override
    public E call(T param) {
        Model obj = param.getValue();
        return (E) new ReadOnlyObjectWrapper(this.func.apply(obj));
    }
 }