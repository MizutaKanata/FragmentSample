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
import android.widget.Button;
import android.widget.TextView;

public class MenuThanksFragment extends Fragment {
    private boolean _isLayoutXLarge = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 親クラスのonCreate()の呼び出し。
        super.onCreate(savedInstanceState);

        // フラグメントマネージャーを取得。
        FragmentManager manager = getFragmentManager();
        // フラグメントマネージャーからメニューリストフラグメントを取得。
        MenuListFragment menuListFragment = (MenuListFragment) manager.findFragmentById(R.id.fragmentMenuList);
        // メニューリストフラグメントがnull、つまり存在しないなら…
        if(menuListFragment == null) {
            // 画面判定フラグを通常画面とする。
            _isLayoutXLarge = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // このフラグメントが所属するアクティビティオブジェクトを取得。
        Activity parentActivity = getActivity();
        // フラグメントで表示する画面をXMLファイルからインフレートする。
        View view = inflater.inflate(R.layout.fragment_menu_thanks, container, false);

        // Bundleオブジェクトを宣言。
        Bundle extras;
        // 大画面の場合…
        if(_isLayoutXLarge) {
            //このフラグメントに埋め込まれた引き継ぎデータを取得。
            extras = getArguments();
        }
        // 通常画面の場合…
        else {
            // 所属アクティビティからインテントを取得。
            Intent intent = parentActivity.getIntent();
            // インテントから引き継ぎデータをまとめたもの(Bundleオブジェクト)を取得。
            extras = intent.getExtras();
        }

        // 注文した定食名と金額変数を用意。引き継ぎデータがうまく取得できなかった時のために""で初期化。
        String menuName = "";
        String menuPrice = "";
        // 引き継ぎデータ(Bundleオブジェクト)が存在すれば…
        if(extras != null) {
            // 定食名と金額を取得。
            menuName = extras.getString("menuName");
            menuPrice = extras.getString("menuPrice");
        }
        // 定食名と金額を表示させるTextViewを取得。
        TextView tvMenuName = view.findViewById(R.id.tvMenuName);
        TextView tvMenuPrice = view.findViewById(R.id.tvMenuPrice);
        // TextViewに定食名と金額を表示。
        tvMenuName.setText(menuName);
        tvMenuPrice.setText(menuPrice);

        // 戻るボタンを取得。
        Button btBackButton = view.findViewById(R.id.btBackButton);
        // 戻るボタンにリスナを登録。
        btBackButton.setOnClickListener(new ButtonClickListener());

        // インフレートされた画面を戻り値として返す。
        return view;
    }

    /**
     * ボタンが押されたときの処理が記述されたメンバクラス。
     */
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // 大画面の場合…
            if(_isLayoutXLarge) {
                // フラグメントマネージャーを取得。
                FragmentManager manager = getFragmentManager();
                // フラグメントトランザクションの開始。
                FragmentTransaction transaction = manager.beginTransaction();
                // 自分自身を削除。
                transaction.remove(MenuThanksFragment.this);
                // フラグメントトランザクションのコミット。
                transaction.commit();
            }
            else {
                // このフラグメントが所属するアクティビティオブジェクトを取得。
                Activity parentActivity = getActivity();
                // 自分が所属するアクティビティを終了。
                parentActivity.finish();
            }
        }
    }
}
