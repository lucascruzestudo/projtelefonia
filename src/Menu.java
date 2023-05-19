import java.util.Scanner;

public class Menu {

    private GerenciadorEntrada input;

    public Menu() {
        this.input = GerenciadorEntrada.getInstancia();
    }
    
     public void exibirMenu() {
       Telefonia telefonia = new Telefonia();
       int opcao = 0;
       boolean exibirOpcoes = true;

       do {
           exibirOpcoesMenu(exibirOpcoes);
           exibirOpcoes = true;
           opcao = input.getOpcao();

           switch (opcao) {
               case 1:
                   System.out.println("Cadastrar assinante...");
                   telefonia.cadastrarAssinante();
                   break;
               case 2:
                   System.out.println("Listar assinantes...");
                   telefonia.listarAssinante();
                   break;
               case 3:
                   System.out.println("Fazer chamada...");
                   telefonia.fazerChamada();
                   break;
               case 4:
                   System.out.println("Fazer recarga...");
                   telefonia.fazerRecarga();
                   break;
               case 5:
                   System.out.println("Imprimir faturas...");
                   telefonia.imprimirFaturas();
                   break;
               case 6:
                   System.out.println("Sair do programa...");
                   telefonia.sairDoPrograma();
                   break;
               default:
                   exibirOpcoes = false;
                   System.out.println("Opção inválida...");
           }

       } while(opcao != 6);
   }

   public void exibirOpcoesMenu(boolean exibirOpcoes) {
        if (exibirOpcoes) {
            System.out.println("\n\n(1) Cadastrar assinante.");
            System.out.println("(2) Listar assinantes.");
            System.out.println("(3) Fazer chamada.");
            System.out.println("(4) Fazer recarga.");
            System.out.println("(5) Imprimir faturas.");
            System.out.println("(6) Sair do programa.");
        }
   }

}

