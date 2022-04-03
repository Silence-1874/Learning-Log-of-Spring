package com.silence.controller;

import com.silence.DO.DiscussPostDO;
import com.silence.DO.UserDO;
import com.silence.VO.DiscussPostPageVO;
import com.silence.service.DiscussPostService;
import com.silence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, DiscussPostPageVO page) {
        page.setRows(discussPostService.countRows(0));
        page.setPath("/index");

        int offset = page.getOffSet();
        List<DiscussPostDO> list = discussPostService.listPage(0, page.getOffSet(), page.getPageSize());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPostDO post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                UserDO user = userService.getById(post.getUserId());
                map.put("user", user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        model.addAttribute("page", page);
        return "/index";
    }

}