package com.hello_user.hello_user;

import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {
    // Listan som våra medlemmar kommer sparas i. Lägger till en admin-medlem direkt när listan skapas.
    private static final List<Member> members = new ArrayList<>(Arrays.asList(new Member("admin", "admin")));

    @GetMapping("/")
    String getIndex(Model model) {
        model.addAttribute("adminUsername", members.get(0).getUsername());
        return "index";
    }

    @GetMapping("/members")
    String getMember(Model model) {
        model.addAttribute("adminUsername", members.get(0).getUsername());
        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping("/login")
    String getLogin(Model model) {
        model.addAttribute("adminUsername", members.get(0).getUsername());
        model.addAttribute("adminPassword", members.get(0).getPassword());
        return "login";
    }

    @GetMapping("/new-member")
    String getJoin(Model model) {
        model.addAttribute("adminUsername", members.get(0).getUsername());
        model.addAttribute("newMember", new Member());
        return "new-member";
    }

    @GetMapping("/remove-member/{memberUsername}")
    String removeMember(@PathVariable String memberUsername) {
        members.removeIf(member -> member.getUsername().equals(memberUsername));
        return "redirect:/members";
    }

    @PostMapping("/new-member")
    String newMember(@RequestParam("username") String username, @RequestParam("password") String password) {
        // Finns en medlem med angivet användarnamn redan? Då tar vi bort den. Gillar inte tanken att flera användare kan ha samma användarnamn.
        members.removeIf(member -> member.getUsername().equals(username));
        // Lägger till ny användare.
        members.add(new Member(username, password));
        return "redirect:/members";
    }
}
