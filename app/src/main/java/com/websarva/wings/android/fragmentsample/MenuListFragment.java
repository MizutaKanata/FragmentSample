package com.websarva.wings.android.fragmentsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListFragment extends Fragment {
    private boolean _isLayoutXLarge = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // このフラグメントが所属するアクティビティオブジェクトを取得。
        Activity parentActivity = getActivity();

        // フラグメントで表示する画面をXMLファイルからインフレートする。
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        // 画面部品ListViewを取得
        ListView lvMenu = view.findViewById(R.id.lvMenu);
        // SimpleAdapterで使用するListオブジェクトを用意。
        List<Map<String, String>> menuList = new ArrayList<>();

        //「から揚げ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
        Map<String, String> menu = new HashMap<>();
        menu.put("name", "から揚げ定食");
        menu.put("price", "800円");
        menuList.add(menu);

        //「ハンバーグ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
        menu = new HashMap<>();
        menu.put("name", "ハンバーグ定食");
        menu.put("price", "850円");
        menuList.add(menu);

        // 以下データ登録の繰り返し。
        menu = new HashMap<>();
        menu.put("name", "生姜焼き定食");
        menu.put("price", "850円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ステーキ定食");
        menu.put("price", "1000円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "野菜炒め定食");
        menu.put("price", "750円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "とんかつ定食");
        menu.put("price", "900円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ミンチかつ定食");
        menu.put("price", "850円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "チキンカツ定食");
        menu.put("price", "900円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "コロッケ定食");
        menu.put("price", "850円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "回鍋肉定食");
        menu.put("price", "750円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "麻婆豆腐定食");
        menu.put("price", "800円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "青椒肉絲定食");
        menu.put("price", "900円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "焼き魚定食");
        menu.put("price", "850円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "焼肉定食");
        menu.put("price", "950円");
        menuList.add(menu);

        // SimpleAdapter第4引数from用データの用意。
        String[] from = {"name", "price"};
        // SimpleAdapter第5引数to用データの用意。
        int[] to = {android.R.id.text1, android.R.id.text2};
        // SimpleAdapterを生成。
        SimpleAdapter adapter = new SimpleAdapter(parentActivity, menuList, android.R.layout.simple_list_item_2, from, to);
        // アダプタの登録。
        lvMenu.setAdapter(adapter);

        // リスナの登録。
        lvMenu.setOnItemClickListener(new ListItemClickListener());

        // インフレートされた画面を戻り値として返す。
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // 親クラスのメソッド呼び出し。
        super.onActivityCreated(savedInstanceState);
        // このフラグメントが所属するアクティビティオブジェクトを取得。
        Activity parentActivity = getActivity();
        // 自分が所属するアクティビティからmenuThanksFrameを取得。
        View menuThanksFrame = parentActivity.findViewById(R.id.menuThanksFrame);
        // menuThanksFrameがnull、つまり存在しないなら…
        if(menuThanksFrame == null) {
            // 画面判定フラグを通常画面とする。
            _isLayoutXLarge = false;
        }
    }

    /**
     * リストがタップされたときの処理が記述されたメンバクラス。
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // タップされた行のデータを取得。SimpleAdapterでは1行分のデータはMap型!
            Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);
            // 定食名と金額を取得。
            String menuName = item.get("name");
            String menuPrice = item.get("price");

            // このフラグメントが所属するアクティビティオブジェクトを取得。
            Activity parentActivity = getActivity();

            // 引き継ぎデータをまとめて格納できるBundleオブジェクト生成。
            Bundle bundle = new Bundle();
            // Bundleオブジェクトに引き継ぎデータを格納。
            bundle.putString("menuName", menuName);
            bundle.putString("menuPrice", menuPrice);

            // 大画面の場合。
            if(_isLayoutXLarge) {
                // フラグメントマネージャーの取得。
                FragmentManager manager = getFragmentManager();
                // フラグメントトランザクションの開始。
                FragmentTransaction transaction = manager.beginTransaction();
                // 注文完了フラグメントを生成。
                MenuThanksFragment menuThanksFragment = new MenuThanksFragment();
                // 引き継ぎデータを注文完了フラグメントに格納。
                menuThanksFragment.setArguments(bundle);
                // 生成した注文完了フラグメントをmenuThanksFrameレイアウト部品に追加(置き換え)。
                transaction.replace(R.id.menuThanksFrame, menuThanksFragment);
                // フラグメントトランザクションのコミット。
                transaction.commit();
            }
            // 通常画面の場合。
            else {
                // インテントオブジェクトを生成。
                Intent intent = new Intent(parentActivity, MenuThanksActivity.class);
                // 第2画面に送るデータを格納。ここでは、Bundleオブジェクトとしてまとめて格納。
                intent.putExtras(bundle);
                // 第2画面の起動。
                startActivity(intent);
            }
        }
    }
}
