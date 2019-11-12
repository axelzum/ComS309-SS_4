package com.ss4.opencampus.backend.websocket;

import com.ss4.opencampus.backend.database.uspots.USpot;
import com.ss4.opencampus.backend.database.uspots.USpotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/websocket/{studentID}", configurator = CustomConfigurator.class)
@Component
public class WebSocketServer
{

  //That doesn't remove users when they logout
  private static Map<Session, Integer> sessionStudentIDMap = new HashMap<>();
  private static Map<Integer, Session> studentIDSessionMap = new HashMap<>();

//  @Autowired
//  private StudentRepository studentRepository;

  @Autowired
  private static USpotRepository uSpotRepository;

  private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

  @OnOpen
  public void onOpen(Session session, @PathParam("studentID") Integer studentID)
  {
    logger.info("New Student entered WebSocket. Student ID = " + studentID);
    sessionStudentIDMap.put(session, studentID);
    studentIDSessionMap.put(studentID, session);
//    String userName = studentRepository.findById(studentID).get().getUserName();
//    String openMsg = userName + " has joined the chatroom." + new SimpleDateFormat("HH:mm").format(new Date());
//    send opening msg to everyone
//    displayMsg(openMsg);
  }

  @OnMessage
  public static void onMessage(Integer uSpotID)
  {
    //logger.info("In \"Message\" method. ID of USpot commented on: " + uSpotID);
    try
    {
      USpot u;
      if(uSpotRepository.findById(uSpotID).isPresent())
        u = uSpotRepository.findById(uSpotID).get();
      else
      {
        throw new Exception("Not a valid USpot!");
      }
      Integer uSpotOwner = u.getStudentId();
      String msg = "A new user just left a review on your USpot " + u.getUsName();
      //send msg to specific User
      displayMsg(msg, uSpotOwner);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

  }

  @OnClose
  public void onClose(Session session)
  {
    logger.info("Student is logging out of application. Now removing them from WebSocket");
    Integer studentID = sessionStudentIDMap.get(session);
    sessionStudentIDMap.remove(session);
    studentIDSessionMap.remove(studentID);

    //String username = studentRepository.findById(studentID).get().getUserName();
    //String exitMsg = username + " disconnected. " + new SimpleDateFormat("HH:mm").format(new Date());
    //send to all
    //displayMsg(exitMsg);
  }

  @OnError
  public void onError(Session session, Throwable throwable)
  {
    logger.info("In error method");
  }

  private static void displayMsg(String msg, Integer id)
  {
    try
    {
      //if student is connected to websocket. Send them a message
      if(studentIDSessionMap.containsKey(id))
        studentIDSessionMap.get(id).getBasicRemote().sendText(msg);
      else
      {
        //Otherwise don't send anything
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
//    sessionStudentIDMap.forEach((session, studentID) -> {
//      synchronized (session) {
//        try
//        {
//          session.getBasicRemote().sendText(msg);
//        }
//        catch (IOException e)
//        {
//          e.printStackTrace();
//        }
//      }
//    });
  }


}
