package com.ss4.opencampus.backend.websocket;

import com.ss4.opencampus.backend.database.uspots.USpot;
import com.ss4.opencampus.backend.database.uspots.USpotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Willis Knox
 */
@ServerEndpoint(value = "/websocket/{studentID}", configurator = CustomConfigurator.class)
@Component
public class WebSocketServer
{

  private static Map<Session, Integer> sessionStudentIDMap = new HashMap<>();
  private static Map<Integer, Session> studentIDSessionMap = new HashMap<>();

  private static USpotRepository uSpotRepository;

  @Autowired
  private USpotRepository temp;

  private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

  /**
   * After the WebSocketServer Bean has been made, assign the static variable the value of temp
   */
  @PostConstruct
  public void init()
  {
    WebSocketServer.uSpotRepository = temp;
  }

  /**
   * When a new Student connects to the WebSocket i.e. they log in, this method is called to add them to the session
   * maps
   *
   * @param session
   *         Session to be added
   * @param studentID
   *         id of Student that just connected
   */
  @OnOpen
  public void onOpen(Session session, @PathParam("studentID") Integer studentID)
  {
    logger.info("New Student entered WebSocket. Student ID = " + studentID);
    sessionStudentIDMap.put(session, studentID);
    studentIDSessionMap.put(studentID, session);
  }

  /**
   * Method is called when a Review is made on a USpot. Calls a helper method that sends a notification to the creator
   * of the USpot.
   *
   * @param uSpotID
   *         id of USpot that was reviewed
   */
  @OnMessage
  public static void onMessage(Integer uSpotID)
  {
    try
    {
      USpot u;
      if (uSpotRepository.findById(uSpotID).isPresent())
        u = uSpotRepository.findById(uSpotID).get();
      else
      {
        throw new Exception("Not a valid USpot!");
      }
      Integer uSpotOwner = u.getStudentId();
      Integer msg = u.getId();
      //send msg to specific User
      displayMsg(msg, uSpotOwner);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

  }

  /**
   * When a Student logs out of the app, they are removed from the WebSocket so they won't receive notifications
   *
   * @param session
   *         Session the student that is logging out was a part of
   */
  @OnClose
  public void onClose(Session session)
  {
    logger.info("Student is logging out of application. Now removing them from WebSocket");
    Integer studentID = sessionStudentIDMap.get(session);
    sessionStudentIDMap.remove(session);
    studentIDSessionMap.remove(studentID);
  }

  /**
   * If something goes wrong, this method is called
   *
   * @param session
   *         Session that threw error
   * @param throwable
   *         Throwable error that occurred
   */
  @OnError
  public void onError(Session session, Throwable throwable)
  {
    logger.info("In error method");
  }

  /**
   * Private helper method that actually sends the notification to the creator of the USpot that was reviewed
   *
   * @param msg
   *         id of USpot that was reviewed
   * @param id
   *         id of Student that made the USpot that was reviewed
   */
  private static void displayMsg(Integer msg, Integer id)
  {
    try
    {
      //if student is connected to websocket, Send them a message
      if (studentIDSessionMap.containsKey(id))
        studentIDSessionMap.get(id).getBasicRemote().sendText(msg.toString());
      //Otherwise don't send anything
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }


}
