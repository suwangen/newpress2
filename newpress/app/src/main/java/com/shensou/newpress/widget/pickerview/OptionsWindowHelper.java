package com.shensou.newpress.widget.pickerview;

import android.app.Activity;

import com.shensou.newpress.bean.ChooseAddressGson;

import java.util.ArrayList;
import java.util.List;


/**
 * 地址选择�?
 *
 * @version 0.1 king 2015-10
 */
public class OptionsWindowHelper {

    private static List<String> options1Items = null;
    private static List<List<String>> options2Items = null;
    private static List<List<List<String>>> options3Items = null;

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int option1, int option2, int option3);
    }

    private OptionsWindowHelper() {
    }

    public static CharacterPickerWindow builder(Activity activity, List<ChooseAddressGson.ResponseEntity> response, final OnOptionsSelectListener listener) {
        //选项选择�?
        CharacterPickerWindow mOptions = new CharacterPickerWindow(activity);
        //初始化�?�项数据
        setPickerData(mOptions.getPickerView(),response);
        //设置默认选中的三级项�?
        mOptions.setSelectOptions(0, 0, 0);
        //监听确定选择按钮
        mOptions.setOnoptionsSelectListener(new OnOptionChangedListener() {
            @Override
            public void onOptionChanged(int option1, int option2, int option3) {
                if (listener != null) {
                    String province = options1Items.get(option1);
                    String city = options2Items.get(option1).get(option2);
                    String area = options3Items.get(option1).get(option2).get(option3);
                    listener.onOptionsSelect( option1,  option2,  option3);
                }
            }
        });
        return mOptions;
    }

    /**
     * 初始化�?�项数据
     */
    public static void setPickerData(CharacterPickerView view,List<ChooseAddressGson.ResponseEntity> response) {
        if (options1Items == null) {
            options1Items = new ArrayList<>();
            options2Items = new ArrayList<>();
            options3Items = new ArrayList<>();

            for (int i = 0; i <response.size() ; i++) {
                options1Items.add(response.get(i).getTitle());
                List<String> options2Items01 = new ArrayList<>();
                List<List<String>> options3Items01 = new ArrayList<>();
                for (int j = 0; j<response.get(i).getCity_list().size() ; j++) {
                    options2Items01.add(response.get(i).getCity_list().get(j).getTitle());
                    List<String> value2 = new ArrayList<>();
                    for (int k = 0; k <response.get(i).getCity_list().get(j).getCounty_list().size() ; k++) {

                        value2.add(response.get(i).getCity_list().get(j).getCounty_list().get(k).getTitle());
                    }
                    options3Items01.add(value2);
                }
                options2Items.add(options2Items01);
                options3Items.add(options3Items01);
            }

        }

        //三级联动效果
        view.setPicker(options1Items, options2Items, options3Items);

    }

}
