package com.example.gabrielesparza.task_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class TaskListActivity extends AppCompatActivity
{
    //                                                      //tag for Log.d() method.
    final static String tag = "GREM";
    BroadcastReceiver showTaskReceiver = new ShowTaskReceiver();

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        //                                                  //To register a receiver of the broadcast
        IntentFilter intentFilter = new IntentFilter("com.GREM.CUSTOM_INTENT.TasksReady");
        this.registerReceiver(this.showTaskReceiver, intentFilter);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onResume()
    //                                                  //Called when the activity has become visible.
    {
        super.onResume();
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetAllTask(taskDBInstance, getApplicationContext());
    }
    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onPause()
    //                                                      //Called when another activity is taking focus.
    {
        super.onPause();
        Log.d(tag, "The onPause() event");

        //                                                  //To unregister a receiver of the broadcast
        Log.d(tag, "Unregistrando....");
        this.unregisterReceiver(this.showTaskReceiver);
    }
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void onDestroy()
    //                                                      //Called just before the activity is destroyed.
    {
        super.onDestroy();
        //                                                  //Debugging purpose.
        Log.d(tag, "The onDestroy() event");
        //                                                  //Destroy de DB INSTANCE.
        TaskDB.destroyInstance();
    }

    //------------------------------------------------------------------------------------------------------------------
    public void ShowAllTask(View view)
    //                                                  //onClick-btnShowAll (Show All button)
    {
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetAllTask(taskDBInstance, getApplicationContext());
    }

    //------------------------------------------------------------------------------------------------------------------
    public void ShowToDoTask(View view)
    //                                                  //onClick-btnToDo (To Do button)
    {
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetToDoTask(taskDBInstance, getApplicationContext());
    }

    //------------------------------------------------------------------------------------------------------------------
    public void ShowDoingTaskForm(View view)
    //                                                  //onClick-btnDoing (Doing button)
    {
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetDoingTask(taskDBInstance, getApplicationContext());
    }

    //------------------------------------------------------------------------------------------------------------------
    public void ShowDoneTaskForm(View view)
    //                                                  //onClick-btnDone (Done button)
    {
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetDoneTask(taskDBInstance, getApplicationContext());
    }

    //------------------------------------------------------------------------------------------------------------------
    private class ShowTaskReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //                                              //When com.LGF.CUSTOM_INTENT.TasksReady occurs

            List<Task> listOfTask = DBUtil.getTasks();
            for (Task task: listOfTask){
                Log.d("GREM - Tasks ", task.getShortDescription() + ", " +
                        String.valueOf(task.getPercentage()));
            }


            //                                                  //Referencing RecyclerView
            RecyclerView recyclerView = findViewById(R.id.recyclerViewTasks);

            //                                                  //Setting the adapter for the RecyclerList
            ListTaskAdapter listTaskAdapter = new ListTaskAdapter(listOfTask);
            recyclerView.setAdapter(listTaskAdapter);

            //                                                  //Setting the manager for the RecyclerList
            LinearLayoutManager manager = new LinearLayoutManager(
                    getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);

        }
    }

    //------------------------------------------------------------------------------------------------------------------
}
/*END-ACTIVITY*/