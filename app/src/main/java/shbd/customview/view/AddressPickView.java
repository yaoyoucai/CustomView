package shbd.customview.view;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.view.ContextThemeWrapper;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import shbd.customview.R;


/**
 * 项目名称：AddressSelect
 * 类描述：
 * 创建人：yh
 * 创建时间：2016/12/15 10:27
 * 修改人：yh
 * 修改时间：2016/12/15 10:27
 * 修改备注：
 */
public class AddressPickView extends PopupWindow implements PopupWindow.OnDismissListener, View.OnClickListener, TabLayout.OnTabSelectedListener, AdapterView.OnItemClickListener {
    private TextView mTvCancel;
    private TabLayout mTbSelector;
    private View mView;
    private Activity mActivity;
    private ListView mLvContent;
    //控件高度
    private int height;

    private Cursor mProvinceData;
    private Cursor mCityData;
    private Cursor mDistrictData;

    private SimpleCursorAdapter mAdapter;

    private OnPickerClickListener mListener;

    //当前选中项的下标
    private int mProvincePosition;
    private int mCityPosition;
    private int mDistrictPosition;

    public void setOnPickerClickListener(OnPickerClickListener listener) {
        this.mListener = listener;
    }

    public AddressPickView(Activity activity) {
        this.mActivity = activity;
        if (height == 0) {
            height = getScreenH(mActivity) / 2;
        }
        initPicker();
        createLocalDataBase();
        initView();
    }


    private void initPicker() {
        ContextThemeWrapper ctx = new ContextThemeWrapper(mActivity.getApplicationContext(), R.style.AppTheme);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        mView = inflater.inflate(R.layout.address_picker_view, null);
        this.setContentView(mView);

        //宽度和高度必需要设，否则无法显示
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(height);

        this.setAnimationStyle(R.style.AddressPickViewAnimStyle);
        //点击外部消失   不会消失的原因http://www.cnblogs.com/popfisher/p/5608717.html
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
    }

    /**
     * 创建本地数据库
     */

    private void createLocalDataBase() {
        DBManager dbHelper = new DBManager(mActivity);
        dbHelper.openDatabase();
        dbHelper.closeDatabase();
    }

    private Cursor queryDataFromLocal(String statement) {
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
        Cursor cursor = database.rawQuery(statement, null);
        return cursor;
    }

    private void initView() {
        mTvCancel = (TextView) mView.findViewById(R.id.tv_cancel);
        mLvContent = (ListView) mView.findViewById(R.id.lv_content);
        mTbSelector = (TabLayout) mView.findViewById(R.id.tb_selector);

        mTvCancel.setOnClickListener(this);
        mTbSelector.setOnTabSelectedListener(this);
        mLvContent.setOnItemClickListener(this);
        setOnDismissListener(this);

        mProvinceData = queryDataFromLocal("select distinct(shortname) as _id from city where level=1");
        mAdapter = new SimpleCursorAdapter(mActivity, R.layout.list_item_city, mProvinceData,
                new String[]{"_id"}, new int[]{R.id.textview}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mLvContent.setAdapter(mAdapter);

        mTbSelector.addTab(mTbSelector.newTab().setText("请选择"));
        mTbSelector.getTabAt(0).select();
    }


    public void show(View view) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        mActivity.getWindow().setAttributes(lp);
        showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    public int getScreenH(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    @Override
    public void onDismiss() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 1f;
        mActivity.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_cancel) {
            dismiss();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Cursor mData = null;
        int currentPosition = 0;
        switch (tab.getPosition()) {
            case 0:
                mData = mProvinceData;
                currentPosition = mProvincePosition;
                break;
            case 1:
                mData = mCityData;
                currentPosition = mCityPosition;
                break;
            case 2:
                mData = mDistrictData;
                currentPosition = mDistrictPosition;
                break;
        }
        mAdapter.swapCursor(mData);
        mLvContent.setSelection(currentPosition);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private String first = "";
    private String second = "";
    private String third = "";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) mAdapter.getItem(position);
        cursor.moveToPosition(position);
        String data = cursor.getString(cursor.getColumnIndex("_id"));
        int currentPosition = mTbSelector.getSelectedTabPosition();

        mTbSelector.removeAllTabs();
        Cursor mData = null;
        switch (currentPosition) {
            case 0:
                mProvincePosition = position;
                first = data;
                mCityData = queryDataFromLocal("select distinct(name) as _id from city where pid in(select distinct(id) from city where shortname='" + data + "' and level=1)");
                mTbSelector.addTab(mTbSelector.newTab().setText(first));
                if (mCityData.moveToFirst()) {
                    mData = mCityData;
                    mTbSelector.addTab(mTbSelector.newTab().setText("请选择"));
                    mTbSelector.getTabAt(1).select();
                } else {
                    second = "";
                    third = "";
                    mData = mProvinceData;
                    mTbSelector.getTabAt(0).select();
                    queryZipCode(data);
                }

                break;
            case 1:
                mCityPosition = position;
                second = data;
                mDistrictData = queryDataFromLocal("select distinct(name) as _id from city where pid= (select distinct(id) from city where name ='" + data + "'  and level=2)");
                mData = mDistrictData;
                mTbSelector.addTab(mTbSelector.newTab().setText(first));
                mTbSelector.addTab(mTbSelector.newTab().setText(second));
                if (mDistrictData.moveToFirst()) {
                    mData = mDistrictData;
                    mTbSelector.addTab(mTbSelector.newTab().setText("请选择"));
                    mTbSelector.getTabAt(2).select();
                } else {
                    third = "";
                    mData = mCityData;
                    mTbSelector.getTabAt(1).select();
                    queryZipCode(data);
                }
                break;
            case 2:
                mDistrictPosition = position;
                mData = mDistrictData;
                third = data;
                mTbSelector.addTab(mTbSelector.newTab().setText(first));
                mTbSelector.addTab(mTbSelector.newTab().setText(second));
                mTbSelector.addTab(mTbSelector.newTab().setText(third));
                mTbSelector.getTabAt(2).select();
                queryZipCode(data);
                break;
        }
        mAdapter.swapCursor(mData);
    }

    /**
     * 查询邮编
     *
     * @param name
     * @return
     */
    private void queryZipCode(String name) {
        Cursor cursor = queryDataFromLocal("select distinct(zip_code) as _id from city where name ='" + name + "'");
        cursor.moveToFirst();
        String zipCode = cursor.getString(cursor.getColumnIndex("_id"));
        if (mListener != null) {
            mListener.onPickerClick(first + second + third, zipCode);
        }
        dismiss();
    }

    public interface OnPickerClickListener {
        void onPickerClick(String selectData, String zipCode);
    }
}
