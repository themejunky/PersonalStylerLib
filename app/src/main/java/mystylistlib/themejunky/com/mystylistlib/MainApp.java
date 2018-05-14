package mystylistlib.themejunky.com.mystylistlib;

import android.app.Application;
import android.os.StrictMode;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

}
