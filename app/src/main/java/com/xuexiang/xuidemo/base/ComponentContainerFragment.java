package com.xuexiang.xuidemo.base;

import android.view.View;
import android.widget.AdapterView;

import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.base.XPageContainerListFragment;
import com.xuexiang.xuidemo.adapter.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xuexiang.xuidemo.adapter.SimpleAdapter.KEY_SUB_TITLE;
import static com.xuexiang.xuidemo.adapter.SimpleAdapter.KEY_TITLE;

/**
 * 解决输入法内存泄漏, 修改列表样式为主副标题显示
 *
 * @author xuexiang
 * @since 2018/11/22 上午11:26
 */
public abstract class ComponentContainerFragment extends XPageContainerListFragment {

    @Override
    protected void initData() {
        mSimpleData = initSimpleData(mSimpleData);

        List<Map<String, String>> data = new ArrayList<>();
        for (String content : mSimpleData) {
            Map<String, String> item = new HashMap<>();
            int index = content.indexOf("\n");
            if (index > 0) {
                item.put(KEY_TITLE, String.valueOf(content.subSequence(0, index)));
                item.put(KEY_SUB_TITLE, String.valueOf(content.subSequence(index + 1, content.length())));
            } else {
                item.put(KEY_TITLE, content);
                item.put(KEY_SUB_TITLE, "");
            }
            data.add(item);
        }

        getListView().setAdapter(new SimpleAdapter(getContext(), data));
        initSimply();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        onItemClick(view, position);
    }

    @SingleClick
    private void onItemClick(View view, int position) {
        onItemClick(position);
    }

    @Override
    public void onDestroyView() {
//        KeyboardUtils.fixSoftInputLeaks(getContext());

        getListView().setOnItemClickListener(null);
        super.onDestroyView();
    }
}
