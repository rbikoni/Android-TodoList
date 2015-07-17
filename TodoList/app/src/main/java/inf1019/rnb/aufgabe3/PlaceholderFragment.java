package inf1019.rnb.aufgabe3;

/**
 * Created by rnb on 10.04.2015.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * This Fragment represents the Main-View of the Todo-List-Items
 * The Main-View should contain all Todo-List-Items
 * Here it should be possible to "finish" or "unfinish" the listed Todo-List-Items.
 *
 * Here the user should have the following possibilities:
 *  + switch to a new view/fragment, add a Todo-List-Item
 *  + delete a Todo-List-Item
 *  + finish or unfinish a Todo-List-Item
 *
 * Design:
 *  ListView
 *  ActionMenu
 *
 * Navigation:
 *  Back: Close
 *  Next: Detail-View of a new Todo-List-Item
 */

public class PlaceholderFragment extends Fragment
implements ExpandOrCollapseGroupListener
{
    private TodoList todoList = new TodoList();
    private TodoListAdapter todoArrayAdaper = null;
    ExpandableListView lv_todos = null;

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, null);
        todoArrayAdaper = new TodoListAdapter(getActivity(), todoList, this);

        lv_todos = (ExpandableListView)rootView.findViewById(R.id.lv_todos);
        lv_todos.setAdapter(todoArrayAdaper);
        lv_todos.setGroupIndicator(null);
        // Without this, it's impossible to check multiple Todo-List-Items
        lv_todos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        /* wont be called, unless checkbox will be removed
        lv_todos.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.v("EXP","GroupCLICKED");
                return false;
            }
        });
        */

        // First time application has been started?
        firstStart();
        // Resort expand or collapsed state
        expandOrCollapseLoadedItems();;
        return rootView;
    }

    private void expandOrCollapseLoadedItems() {
        for(int i=0; i<todoList.size(); ++i) {
            Todo todo = todoList.get(i);
            expandOrCollapseGroupOnClickEvent(i, ! todo.isExpanded());
        }
    }

    /* Restore the selection of the stored Todo-List-Items */

    public void addTodo(Todo todo) {
        todoList.add(todo);
    }

    public TodoList getTodos() { return todoList; }

    private boolean firstStart() {
        SharedPreferences p = getActivity().getPreferences(Activity.MODE_PRIVATE);
        boolean isFirstStart = p.getBoolean("isFirstStart", true);
        if(isFirstStart) {
            Resources resources = getResources();
            SharedPreferences.Editor editor = p.edit();

            editor.putBoolean("isFirstStart", false);
            editor.commit();

            Todo todo = new Todo();
            todo.setTitle(resources.getString(R.string.todo_fristStart_title));
            todo.setContent(resources.getString(R.string.todo_firstStart_content));
            todo.setCategory(Category.OTHER);
            todo.setExpanded(true);
            addTodo(todo);
        }

        return isFirstStart;
    }

    @Override
    public void expandOrCollapseGroupOnClickEvent(int groupPosition, boolean isExpanded) {
        if(! isExpanded) {
            lv_todos.expandGroup(groupPosition, true);
        }
        else {
            lv_todos.collapseGroup(groupPosition);
        }
    }
}