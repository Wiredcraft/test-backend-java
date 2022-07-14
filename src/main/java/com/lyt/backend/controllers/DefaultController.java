package com.lyt.backend.controllers;

import com.lyt.backend.models.UserInfoDTO;
import com.lyt.backend.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(
        tags = {"Just for a front page"}
)
@RestController
public class DefaultController {
    @Autowired
    private UserInfoService userInfoService;
    @RequestMapping(value = "/", produces = "text/html", method = {RequestMethod.GET})
    public ResponseEntity<String> homepage() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfoDTO userInfo = userInfoService.getUserInfo(userDetails.getUsername());
        String html = String.format("""
                <html>
                <head>My Profile</head>
                <table>
                          <tr>
                            <th>Field Name</th>
                            <th>Field Value</th>
                          </tr>
                          <tr>
                            <td>id</td>
                            <td>%s</td>
                          </tr>
                          <tr>
                            <td>name</td>
                            <td>%s</td>
                          </tr>
                          <tr>
                            <td>Date of Birth</td>
                            <td>%s</td>
                          </tr>
                          <tr>
                            <td>Address of User</td>
                            <td>%s</td>
                          </tr>
                          <tr>
                            <td>Description of User</td>
                            <td>%s</td>
                          </tr>
                          <tr>
                            <td>Time the user was created</td>
                            <td>%s</td>
                          </tr>
                          <tr>
                            <td>Time the record was modified</td>
                            <td>%s</td>
                          </tr>
                        </table>
                        <h1>You can use command like: 'curl "localhost:8080/v1/user" -d "address=1234567" -H "Cookie: JSESSIONID=771885504C2AAD795E477BC7398EEEA7"' to send request to other apis that needed basic auth. The JSESSIONID is obtained from F12 Developer Options.</h1>
                </html>
                """, userInfo.getId(), userInfo.getName(), userInfo.getDob(), userInfo.getAddress(), userInfo.getDescription(), userInfo.getCreatedAt(), userInfo.getUpdatedAt());
        return ResponseEntity.ok(html);
    }
}
