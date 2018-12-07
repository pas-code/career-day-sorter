//Edward Fominykh
//Program Description
//Dec 3, 2018

package com.atcs.career.ui.home;

import java.util.ArrayList;

import com.atcs.career.data.Session;
import com.atcs.career.data.Student;

public class StudentInfoUtil extends InfoPanel
{

    public StudentInfoUtil(Student s, int period)
    {
        super(s.getFullName(), s.getGrade()+"", s.getAssignments().get(period).getTitle());
        // TODO Auto-generated constructor stub
    }
    
}
