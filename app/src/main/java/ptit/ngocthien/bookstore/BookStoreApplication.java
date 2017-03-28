package ptit.ngocthien.bookstore;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by hoangtien on 19/03/2017.
 */

public class BookStoreApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Iconify.with(new FontAwesomeModule());
    }
}
