package tads.eaj.ufrn.envioarquivomvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tads.eaj.ufrn.envioarquivomvc.model.Pessoa;
import tads.eaj.ufrn.envioarquivomvc.service.FileStorageService;
import tads.eaj.ufrn.envioarquivomvc.service.PessoaService;

import java.util.List;

@Controller
public class PessoaController {
    PessoaService service;
    FileStorageService fileStorageService;

    public PessoaController(PessoaService service, FileStorageService fileStorageService) {
        this.service = service;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/cadastrar")
    public String getCadastrarPage(Model model){
        Pessoa p = new Pessoa();
        model.addAttribute("pessoa", p);
        return "cadastrar";
    }

    @PostMapping("/doSalvar")
    public String doSalvar(@ModelAttribute Pessoa p, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        p.setFotoUrl(file.getOriginalFilename());
        service.save(p);
        fileStorageService.save(file);

        redirectAttributes.addFlashAttribute("msg", "Salvo com sucesso");
        return "redirect:/index";
    }

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model){

        List<Pessoa> pessoaList = service.listAll();
        model.addAttribute("pessoas", pessoaList);

        return "index";
    }
}
