package com.ss4.opencampus.backend.database.students.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController
{
  @Autowired
  private RouteRepository routeRepository;

}
