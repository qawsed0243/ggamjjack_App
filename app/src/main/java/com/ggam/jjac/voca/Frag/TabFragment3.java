package com.ggam.jjac.voca.Frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ggam.jjac.voca.Exam.ExamActivity;
import com.ggam.jjac.voca.R;

import static com.ggam.jjac.voca.Frag.FragMain.kinsightSession;

public class TabFragment3 extends Fragment {
    Button exam_btn;
    RadioGroup rd_g;
    public static int num =0;
    private static final String[] LEVEL = new String[] {
            "TOEIC-초급", "TOEIC-중급", "TOEIC-고급","수능-초급","수능-중급","수능-고급"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //화면에 여러 컴포넌트를 붙이기 위해 뷰를 선언하는듯.
        View view = inflater.inflate(R.layout.fragment_tab_fragment3,container,false);
        final RadioButton rd1=(RadioButton)view.findViewById(R.id.radioButton1);
        final RadioButton rd2=(RadioButton)view.findViewById(R.id.radioButton2);
        final RadioButton rd3=(RadioButton)view.findViewById(R.id.radioButton3);
        final RadioButton rd4=(RadioButton)view.findViewById(R.id.radioButton4);
        final RadioButton rd5=(RadioButton)view.findViewById(R.id.radioButton5);
        final RadioButton rd6=(RadioButton)view.findViewById(R.id.radioButton6);


        //라디오 버튼 중복선택제거
        rd1.setOnClickListener(new RadioButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                num=1;
                rd1.setChecked(true);
                rd2.setChecked(false);
                rd3.setChecked(false);
                rd4.setChecked(false);
                rd5.setChecked(false);
                rd6.setChecked(false);
            }
        });
        rd2.setOnClickListener(new RadioButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                num=2;
                rd2.setChecked(true);
                rd1.setChecked(false);
                rd3.setChecked(false);
                rd4.setChecked(false);
                rd5.setChecked(false);
                rd6.setChecked(false);
            }
        });
        rd3.setOnClickListener(new RadioButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                num=3;
                rd3.setChecked(true);
                rd2.setChecked(false);
                rd1.setChecked(false);
                rd4.setChecked(false);
                rd5.setChecked(false);
                rd6.setChecked(false);
            }
        });

        rd4.setOnClickListener(new RadioButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                num=4;
                rd4.setChecked(true);
                rd2.setChecked(false);
                rd3.setChecked(false);
                rd1.setChecked(false);
                rd5.setChecked(false);
                rd6.setChecked(false);
            }
        });

        rd5.setOnClickListener(new RadioButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                num=5;
                rd5.setChecked(true);
                rd2.setChecked(false);
                rd3.setChecked(false);
                rd4.setChecked(false);
                rd1.setChecked(false);
                rd6.setChecked(false);
            }
        });

        rd6.setOnClickListener(new RadioButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                num=6;
                rd6.setChecked(true);
                rd2.setChecked(false);
                rd3.setChecked(false);
                rd4.setChecked(false);
                rd5.setChecked(false);
                rd1.setChecked(false);
            }
        });






        //버튼 등록
        exam_btn = (Button) view.findViewById(R.id.Exam_btn);
        //버튼 이벤트
        exam_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(!rd1.isChecked()&&!rd2.isChecked()&&!rd3.isChecked()&&!rd4.isChecked()&&!rd5.isChecked()&&!rd6.isChecked())
                    Toast.makeText(getContext(), "난이도를 선택해주세요!", Toast.LENGTH_SHORT).show();
                else{
                kinsightSession.addEvent(String.format("시험보기"));
                Intent intent = new Intent(getActivity(),ExamActivity.class);
                startActivity(intent);}
            }
        });
        return view;
    }
}
