package inf1019.rnb.aufgabe3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Here all the Fragments are going to be managed.
 *
 * Storing all Todo-List like this
 * Todo-List into JSON
 * store JSON with SharedPreferences
 *
 * Communication between Fragments:
 *  TodoFragment ----(Todo)---> Activity ----(Todo, add)---> PlaceholderFragment
 *
 */
public class MainActivity extends Activity
        implements TodoFragment.OnTodoTextChangedListener{

    private PlaceholderFragment pf = null;
    private TodoFragment tf = null;
    private Gson gson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (pf == null) pf = new PlaceholderFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.container, pf)
                .commit();

        loadTodoList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveTodoList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_createTodo) {
            displayCreateTodo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveTodoList() {
        gson = new Gson();
        String json = gson.toJson(pf.getTodos());
        SharedPreferences p = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor pe = p.edit();
        pe.putString("todos", json);
        pe.commit();
    }

    private void loadTodoList() {
        SharedPreferences p = getPreferences(MODE_PRIVATE);
        String json = p.getString("todos", "");
        List<Todo> todoList = new Gson().fromJson(json, TodoList.class);
        if(todoList != null)
        for(int i=0; i<todoList.size(); ++i) {
            pf.addTodo(todoList.get(i));
        }
    }

    private void displayCreateTodo() {
        // popBackStack forbids overlay of multiple TodoFragments
        getFragmentManager().popBackStack();
        tf = new TodoFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, tf)
                .addToBackStack("createTodo")
                .commit();
    }
    @Override
    public void onTodoTextChanged(Todo todo) {
        // Passing the todo item to PlaceholderFragment
        if(todo == null) return;
        if(todo.getTitle() == "" || todo.getContent() == "") return;
        pf.addTodo(todo);
    }

}
