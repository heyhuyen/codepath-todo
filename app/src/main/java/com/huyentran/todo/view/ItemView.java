package com.huyentran.todo.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huyentran.todo.R;
import com.huyentran.todo.model.Todo;
import com.huyentran.todo.util.DateUtils;

import java.util.Calendar;

/**
 * Custom ListView row.
 */
public class ItemView extends RelativeLayout {
    private CheckBox cbStatus;
    private TextView tvValue;
    private TextView tvDueDate;

    public interface ItemViewListener {
        void onItemViewCheckBoxToggle(int pos);
    }

    public ItemView(Context c) {
        this(c, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.item_todo, this, true);
        setupChildren();
    }

    public static ItemView inflate(ViewGroup parent) {
        return (ItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
    }

    public void setItem(final int pos, Todo item, final ItemViewListener listener) {
        // todo value
        tvValue.setText(item.getValue());

        // checkbox
        cbStatus.setChecked(item.getStatus());
        cbStatus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemViewCheckBoxToggle(pos);
            }
        });

        // due date
        String dueDateStr = item.getDueDate();
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            Calendar dueDate = DateUtils.getDateFromString(dueDateStr);
            String dueDateText = dueDateStr;
            // check if date is past due or today
            if (DateUtils.isPast(dueDate)) {
                tvDueDate.setTextColor(Color.RED);
            } else if (DateUtils.isToday(dueDate)) {
                dueDateText = getResources().getString(R.string.tv_today);
                tvDueDate.setTextColor(Color.GREEN);
            } else {
                tvDueDate.setTextColor(Color.DKGRAY);
            }
            tvDueDate.setText(String.format(getResources().getString(R.string.tv_due_date_label_format), dueDateText));
        }
    }

    public TextView getValueTextView() {
        return tvValue;
    }

    public TextView getDueDateTextView() {
        return tvDueDate;
    }

    private void setupChildren() {
        tvValue = (TextView) findViewById(R.id.tvValue);
        cbStatus = (CheckBox) findViewById(R.id.cbStatus);
        tvDueDate = (TextView) findViewById(R.id.tvDueDate);
    }
}
