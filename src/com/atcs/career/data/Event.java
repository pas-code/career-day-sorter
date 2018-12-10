//Information Team
//Nov 7, 2018
package com.atcs.career.data;

import java.io.Serializable;
import java.util.ArrayList;

import com.atcs.career.io.IOUtilities;
import com.atcs.career.ui.home.SessionInfoUtil;

public class Event implements Serializable
{
    private static final long serialVersionUID = -7463051683970561540L;
    private ArrayList<Session> sessions = new ArrayList<Session>();
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private String eventName;

    /**
     * Creates a new Event from scratch
     */
    public Event(String name)
    {
        eventName = name;
        students = IOUtilities.loadStudentArray(IOUtilities.importCSV());
        rooms = IOUtilities.loadRoomArray();
        sessions = IOUtilities.loadSessionArray();
    }

    public Event()
    {
        super();
        sessions = new ArrayList<Session>();
        sessions.add(new Session("Business", "Donald Trump"));
        sessions.add(new Session("Investment", "Warren Buffet"));
        sessions.add(new Session("Military", "James Mattis"));
        sessions.add(new Session("Electrical Engineering", "Elon Musk"));
        sessions.add(new Session("Astronomy", "Albert Einstein"));
        sessions.add( new Session("Criminal Defense", "Robert Shapiro"));
        sessions.add(new Session("Intelligence", "James Comey"));
        sessions.add(new Session("Software Development", "Johnny Ive"));
        
        students = new ArrayList<Student>();
        {
         ArrayList<Session> sessions = new ArrayList<Session>();
         sessions.add(this.sessions.get(0));
         sessions.add(this.sessions.get(1));
         sessions.add(this.sessions.get(2));
        
        
        students.add(new Student("Peter", "Pan", "ppeter20@pascack.org",sessions, 0));
        
        students.add(new Student("Jack", "Black", "ppeter20@pascack.org",sessions, 0));
        students.add(new Student("Eric", "Wang", "ppeter20@pascack.org",sessions, 0));
        students.add(new Student("Jarret", "Bierman", "ppeter20@pascack.org",sessions, 0));
        students.add(new Student("Peter", "Pan", "ppeter20@pascack.org",sessions, 0));
        students.add(new Student("Peter", "Pan", "ppeter20@pascack.org",sessions, 0));
        
        rooms = new ArrayList<Room>();
        for(int i = 121; i< 140; i++)
        rooms.add(new Room(i, 30));
        
        
        
        }
        
        eventName = "TEST";
        
    }

    public void save()
    {
    }

    public ArrayList<Session> getSessions()
    {
        return sessions;
    }

    public ArrayList<Student> getStudents()
    {
        return students;
    }

    public ArrayList<Room> getRooms()
    {
        return rooms;
    }

    public String getEventName()
    {
        return eventName;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }
}
