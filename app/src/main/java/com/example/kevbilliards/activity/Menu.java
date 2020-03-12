package com.example.kevbilliards.activity;

        import android.app.ListActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

public class Menu extends ListActivity{

    String classes[] = {"PoolActivity", "GFXSurface", "InfiniteBounce", "HighFriction"};
                            // different activities/ "pages" available for selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, classes));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String selecteditem = classes[position];
        try{
            //Class ourclass= Class.forName("com.example.kevbilliards."+ selecteditem);     //before compartmentalization of components
            Class ourclass= Class.forName("com.example.kevbilliards.activity."+ selecteditem);
            System.out.println("---------------------"+ourclass);
            Intent myintent = new Intent(Menu.this, ourclass);
            startActivity(myintent);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
