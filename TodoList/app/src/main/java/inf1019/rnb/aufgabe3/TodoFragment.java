package inf1019.rnb.aufgabe3;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;


/**
 * This Fragment represents the Detail-View of a Todo-List-Item
 * The Detail-View should contain the
 * content of Todo-List-Item
 * Here it should be possible to modify
 *  + Title
 *  + Content
 *  + Category
 * of a Todo-List-Item
 *
 * Design:
 *  EditText
 *  EditText
 *  Spinner
 *  ActionMenu
 *
 * Navigation:
 *  Back: MainView
 *  Next: new Detail-View of a new Todo-List-Item
 */
public class TodoFragment extends Fragment {
    EditText et_todo_title = null;
    EditText et_todo_content = null;
    Todo todo = new Todo();
    OnTodoTextChangedListener mCallback = null;
    Spinner sp_category = null;
    ArrayAdapter<Category> categoryArrayAdapter = null;

    // --- Create an interface to communicate with PlaceHolderFragment
    interface OnTodoTextChangedListener {
        public void onTodoTextChanged(Todo todo);
    }

    public TodoFragment() {
    }

    // ONKEYDOWN EVENT LISETENER
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_todo_create, container, false);

        // --- content, text watcher
        et_todo_content = (EditText) rootView.findViewById(R.id.et_todoContent);

        et_todo_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                todo.setContent(s.toString());
            }
        });

        // --- title, text watcher
        et_todo_title = (EditText) rootView.findViewById(R.id.et_todoTitle);
        et_todo_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                todo.setTitle(s.toString());

            }
        });

        categoryArrayAdapter = new ArrayAdapter<Category>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                Category.values());

        sp_category = (Spinner)rootView.findViewById(R.id.sp_category);
        sp_category.setAdapter(categoryArrayAdapter);
        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category c;
                switch (position) {
                    case 0:
                        c = Category.WORK;
                        break;
                    case 1:
                        c = Category.EDUCATION;
                        break;
                    case 2:
                        c = Category.SPORT;
                        break;
                    default:
                        c = Category.OTHER;
                }

                todo.setCategory(c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mCallback.onTodoTextChanged(todo);
    }

    // http://developer.android.com/training/basics/fragments/communicating.html
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnTodoTextChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTodoTextChangedListener");
        }
    }

}
