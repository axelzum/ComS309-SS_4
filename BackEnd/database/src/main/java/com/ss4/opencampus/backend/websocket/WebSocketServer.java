package com.ss4.opencampus.backend.websocket;

import com.ss4.opencampus.backend.database.students.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/websocket/{userID}", configurator = CustomConfigurator.class)
@Component
public class WebSocketServer
{

  private static Map<Session, Integer> sessionUserIDMap = new HashMap<>();
  private static Map<Integer, Session> userIDSessionMap = new HashMap<>();

  @Autowired
  private StudentRepository studentRepository;

  private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

  @OnOpen
  public void onOpen(Session session, @PathParam("userID") Integer userID)
  {
    logger.info("in Open");
    sessionUserIDMap.put(session, userID);
    userIDSessionMap.put(userID, session);
    String userName = studentRepository.findById(userID).get().getUserName();
    String openMsg = userName + " has joined the chatroom." + new SimpleDateFormat("HH:mm").format(new Date());
    //send opening msg to everyone
    displayMsg(openMsg);
  }

  @OnMessage
  public void onMessage(Session session, String message)
  {
    logger.info("Entered into Message method. Received message: " + message);
    Integer userID = sessionUserIDMap.get(session);
    String userName = studentRepository.findById(userID).get().getUserName();
    //send msg to everyone
    displayMsg(userName + ':' + message);
  }

  @OnClose
  public void onClose(Session session)
  {
    logger.info("In onClose");
    Integer userID = sessionUserIDMap.get(session);
    sessionUserIDMap.remove(session);
    userIDSessionMap.remove(userID);

    String username = studentRepository.findById(userID).get().getUserName();
    String exitMsg = username + " disconnected. " + new SimpleDateFormat("HH:mm").format(new Date());
    //send to all
    displayMsg(exitMsg);
  }

  @OnError
  public void onError(Session session, Throwable throwable)
  {
    logger.info("In error method");
  }

  private static void displayMsg(String msg)
  {
    sessionUserIDMap.forEach((session, userID) -> {
      synchronized (session) {
        try
        {
          session.getBasicRemote().sendText(msg);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    });
  }


}
