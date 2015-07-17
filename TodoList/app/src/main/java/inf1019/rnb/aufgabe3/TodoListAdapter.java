package inf1019.rnb.aufgabe3;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class TodoListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Todo> todoList;
    private ExpandOrCollapseGroupListener expandOrCollapseListener = null;

    public TodoListAdapter(Context context, ArrayList<Todo> todoList, ExpandOrCollapseGroupListener listener) {
        this.context = context;
        this.todoList = todoList;
        this.expandOrCollapseListener = listener;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return todoList.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        Log.v("EXP","getChildView");
        Todo todo = (Todo) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.todo_list_item_expanded, null);
        }

        TextView tv_fullContent = (TextView) view.findViewById(R.id.tv_fullContent);
        tv_fullContent.setText(todo.getContent());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return todoList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return todoList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        Todo todo = (Todo) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.todo_list_item, null);
        }

        CheckBox cb_done = (CheckBox) view.findViewById(R.id.cb_checked);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo todo = todoList.get(groupPosition);
                expandOrCollapseListener.expandOrCollapseGroupOnClickEvent(groupPosition, todo.isExpanded());
                todo.setExpanded(!todo.isExpanded());
            }
        });
        tv_title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                todoList.remove(groupPosition);
                notifyDataSetChanged();
                return false;
            }
        });
        cb_done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("EXP", "CHECKING");
                Todo todo = todoList.get(groupPosition);
                todo.setChecked(isChecked);
                notifyDataSetChanged();
            }
        });

        tv_title.setText(todo.getTitle());
        tv_category.setText(todo.getCategory().toString());
        cb_done.setChecked(todo.getChecked());
        onGroupExpanded(groupPosition);
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}