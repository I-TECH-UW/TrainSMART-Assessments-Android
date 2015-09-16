package com.surveyapp.rayce.surveyapp2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Rayce on 8/7/2015.
 */


public class MultiTypeListAdapter extends BaseAdapter {
    public DBHelper dbhelp;
    public PersonToAssessments pToA;
    private Context context;
    private LayoutInflater inflater;
    private String[][] data;
    public List<EditPageObject> pageData;
    public HashMap saveData = new HashMap();

    public MultiTypeListAdapter(Context context, List<EditPageObject> pageData, PersonToAssessments pToA) {
        dbhelp = new DBHelper(context);
        this.pToA = pToA;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
        this.pageData = pageData;
    }

    @Override
    public int getCount() {
        return pageData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;

        switch (pageData.get(position)._itemtype) {
            case "text":
                type = 0;
                break;
            case "question110":
                type = 1;
                break;
            case "questiontext":
                type = 2;
                break;
            case "questionyesno":
                type = 3;
                break;
            case "questionmulti":
                type = 4;
                break;
            case "title":
                type = 5;
                break;
        }

        return type;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        View view = convertView;
        EditFragment.ViewHolder holder;
        int type = getItemViewType(position);
        //dumpPageData(pageData);
        holder = new EditFragment.ViewHolder(saveData, pageData, dbhelp, pToA);
        holder.position = position;
        if (convertView == null) {

            switch (type) {
                case 0: //Label
                    view = inflater.inflate(R.layout.edit_label, parent, false);
                    holder.textView = (TextView) view.findViewById(R.id.textq);
                    //holder.textView.setText("Section One");
                    view.setTag(holder);
                    break;

                case 1: //1-10
                    view = inflater.inflate(R.layout.edit_question110, parent, false);
                    holder.textView = (TextView) view.findViewById(R.id.textq);
                    holder.seekBar = (SeekBar) view.findViewById(R.id.seekBar);
                    //holder.textView.setText("Is the candidate good at what they're doing?");
                    view.setTag(holder);
                    break;

                case 2: //Single line editText

                    view = inflater.inflate(R.layout.edit_questiontext, parent, false);
                    holder.textView = (TextView) view.findViewById(R.id.textq);

//                  http://stackoverflow.com/questions/9438676/edittext-in-listview-without-it-recycling-input
//                  holder.editText.setText(pageData.get(position).get_answer());

                    holder.editText = (EditText) view.findViewById(R.id.editText);
                    String oldText = pageData.get(position).get_answer();
                    holder.editText.setText(oldText == null ? "" : oldText);
                    holder.position = position;
                    holder.editText.addTextChangedListener(holder);
                    view.setTag(holder);
                    //Log.d("request!", "case 2: " + position + " " + oldText );
                    break;

                case 3: //Switch
                    view = inflater.inflate(R.layout.edit_questionyesno, parent, false);
                    holder.textView = (TextView) view.findViewById(R.id.textq);
                    holder.switchWidget = (Switch) view.findViewById(R.id.yesnoswitch);
                    //holder.textView.setText("Does the candidate like cats?");
                    view.setTag(holder);
                    break;

                case 4: //Multi line editText
                    view = inflater.inflate(R.layout.edit_questionmulti, parent, false);
                    holder.textView = (TextView) view.findViewById(R.id.textq);
                    holder.editText2 = (EditText) view.findViewById(R.id.editText2);
                    //holder.textView.setText("Does the candidate like the smell of bacon?");
                    view.setTag(holder);
                    break;

                case 5: //Title
                    view = inflater.inflate(R.layout.edit_title, parent, false);
                    holder.textView = (TextView) view.findViewById(R.id.textq);
                    //holder.textView.setText("Assessment, Rayce Rossum, 8/24/2015, 10132027");
                    view.setTag(holder);
                    break;

            }
        } else {

            holder = (EditFragment.ViewHolder) view.getTag();

            switch(type) {
                case 1:
                    break;
                case 2:
                    //Log.d("request!", "else 2: " + position + " " + pageData.get(position).get_answer());
                    //holder.editText.removeTextChangedListener(holder);
                    holder.editText.setText(pageData.get(position).get_answer());
                    holder.position = position;
                    break;
            }
        }

        Set set = saveData.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
                // do something, update view/holder/db ???
//            Log.d("request!", "compare0: " + me.getValue() + ":" + pageData.get(position).get_answer() + ":");
//            Log.d("request!", "compare0: " + me.getKey() + ":" + me.getValue() + ":");
//            Log.d("request!", "compare1: " + position + ":" + pageData.get(position).get_answer() + ":");
//            Log.d("request!", "compare2: " + me.getKey().toString() + ":");
//            Log.d("request!", "compare3: " + Integer.parseInt(me.getKey().toString()) + ":");
                    //+ pageData.get(Integer.getInteger(me.getKey().toString())).get_answer() + ":");

            if(me.getValue().equals(pageData.get(position).get_answer()) ) {}
            else {

                pageData.get(Integer.parseInt(me.getKey().toString())).set_answer(me.getValue().toString());
                //Log.d("request!", "key:newValue: " + me.getKey() + ":" + pageData.get(Integer.parseInt(me.getKey().toString())).get_answer());
                Log.d("request!", "add/update: " +
                                pToA.get_person_id() + " " +
                                pToA.get_date_created() + " " +
                                pToA.get_assessment_id() + " " +
                                pageData.get(Integer.parseInt(me.getKey().toString())).get_assessments_questions_id()
                );
                dbhelp.setEditPageRow(pToA, pageData.get(Integer.parseInt(me.getKey().toString())).get_assessments_questions_id(), me.getValue().toString());
            }
            i.remove();
        }



        holder.textView.setText(pageData.get(position).get_question());

        Log.d("request!", "end position: " + position);
        return view;
    }

    public void dumpPageData(List<EditPageObject> pageDataList) {
        for (EditPageObject eop: pageDataList) {
            Log.d("request!", "dumpPageData: " +
                    eop.get_question() + " " +
                    eop.get_answer() + " " +
                    eop.get_itemtype() + " " +
                    eop.get_itemorder() + " " +
                    eop.get_assessments_questions_id()

            );
        }
    }
}
