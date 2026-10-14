package com.company.intra.controller.system;

import com.company.intra.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/** 메뉴/공통코드 관리 */
@Controller
@RequestMapping("/system")
public class SystemController {

    private final SystemService systemService;

    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @GetMapping("/menu")
    public String menuList(Model model) {
        model.addAttribute("menus", systemService.menus());
        return "system/menuList";
    }

    @PostMapping("/menu/save")
    public String saveMenu(@RequestParam String menuId,
                           @RequestParam(required = false) String upMenuId,
                           @RequestParam String menuNm,
                           @RequestParam(required = false) String url,
                           @RequestParam(defaultValue = "0") int sortNo,
                           @RequestParam(defaultValue = "Y") String useYn) {
        systemService.saveMenu(menuId, upMenuId, menuNm, url, sortNo, useYn);
        return "redirect:/system/menu";
    }

    @PostMapping("/menu/delete")
    public String deleteMenu(@RequestParam String menuId) {
        systemService.deleteMenu(menuId);
        return "redirect:/system/menu";
    }

    @GetMapping("/code")
    public String codeList(@RequestParam(required = false) String grpCd, Model model) {
        model.addAttribute("groups", systemService.codeGroups());
        model.addAttribute("selGrpCd", grpCd);
        if (grpCd != null && !grpCd.isEmpty()) {
            model.addAttribute("codes", systemService.codes(grpCd));
        }
        return "system/codeList";
    }

    @PostMapping("/code/save")
    public String saveCode(@RequestParam String grpCd,
                           @RequestParam String dtlCd,
                           @RequestParam String dtlNm,
                           @RequestParam(defaultValue = "0") int sortNo,
                           @RequestParam(defaultValue = "Y") String useYn) {
        systemService.saveCode(grpCd, dtlCd, dtlNm, sortNo, useYn);
        return "redirect:/system/code?grpCd=" + grpCd;
    }

    @PostMapping("/code/delete")
    public String deleteCode(@RequestParam String grpCd, @RequestParam String dtlCd) {
        systemService.deleteCode(grpCd, dtlCd);
        return "redirect:/system/code?grpCd=" + grpCd;
    }
}
