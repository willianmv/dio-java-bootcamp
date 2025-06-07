import dio.desafio.dominio.BootCamp;
import dio.desafio.dominio.Curso;
import dio.desafio.dominio.Dev;
import dio.desafio.dominio.Mentoria;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Curso curso01 = new Curso();
        curso01.setTitulo("Curso - Java");
        curso01.setDescricao("Aprenda POO com Java");
        curso01.setCargaHoraria(8);

        Curso curso02 = new Curso();
        curso02.setTitulo("Curso - Python");
        curso02.setDescricao("Aprenda automações com Python");
        curso02.setCargaHoraria(4);

        Mentoria mentoria01 = new Mentoria();
        mentoria01.setTitulo("Mentoria de Java");
        mentoria01.setDescricao("Aprofunde os conhecimentos na linguagem Java");
        mentoria01.setData(LocalDate.now());

        BootCamp bootCamp = new BootCamp();
        bootCamp.setNome("BootCamp: Torne-se um Desenvolvedor de Software");
        bootCamp.setDescricao("Aprenda tudo sobre programação");
        bootCamp.getConteudos().add(curso01);
        bootCamp.getConteudos().add(curso02);
        bootCamp.getConteudos().add(mentoria01);

        Dev devPedro = new Dev();
        devPedro.setNome("Pedro");
        devPedro.inscreverBootCamp(bootCamp);
        System.out.println("Conteúdos Inscritos do Pedro: "+ devPedro.getConteudosInscritos());
        devPedro.progredir();
        devPedro.progredir();
        devPedro.progredir();
        System.out.println("--------");
        System.out.println("Conteúdos Inscritos do Pedro: "+devPedro.getConteudosInscritos());
        System.out.println("Conteúdos Concluídos do Pedro: "+devPedro.getConteudosConcluidos());
        System.out.println("XP Total: "+devPedro.calcularTotalXp());


    }

}
