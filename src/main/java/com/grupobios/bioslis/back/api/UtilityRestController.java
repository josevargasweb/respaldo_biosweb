package com.grupobios.bioslis.back.api;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.common.DateFormatterHelper;

@RestController
public class UtilityRestController {

  private static Logger logger = LogManager.getLogger(UtilityRestController.class);

  @GetMapping("/api/db/currentTS")

  ResponseTemplateGen<String> getDBCurrenTS() {

    try {
      Timestamp ts = BiosLisCalendarService.getInstance().getTS();
      return new ResponseTemplateGen<String>(DateFormatterHelper.tsToDatetimeText(ts), 200, "OK");
    } finally {
    }
  }
}
