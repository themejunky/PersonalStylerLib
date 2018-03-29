package mystylistlib.themejunky.com.mystylistlib;

import android.content.Intent;
import android.os.Bundle;

import com.themejunky.personalstylerlib.intropager.IntroPagerActivity;
import com.themejunky.personalstylerlib.intropager.ViewPagerModel;

import java.util.ArrayList;
import java.util.List;

public class TestActivityPager extends IntroPagerActivity  {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<ViewPagerModel> mPages = new ArrayList<>();
            mPages.add(new ViewPagerModel(R.mipmap.poza1,R.string.intro_title_page1,R.string.intro_description1_page1,R.color.start,R.color.center,R.color.end,false));
           /* mPages.add(new ViewPagerModel(R.drawable.poza1,"titlu_2","textul_2",R.color.start,R.color.center,R.color.end,false));
            mPages.add(new ViewPagerModel(R.drawable.poza1,"titlu_3","textul_3",R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent,true));*/
        setContent(mPages,new Intent(this,MainActivity.class));
    }
}
