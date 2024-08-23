// See https://aka.ms/new-console-template for more information


using System;
using System.Collections.Generic;
using System.Diagnostics;
class Aluno{
        public string nome;
        public int idade;
        public string matricula;

        public override string ToString()
        {
            return nome + " - " + idade + " - " + matricula;
        }
    }
class MainClass{


    public static void Menu(){
        Console.WriteLine("1 - Cadastrar Aluno");
        Console.WriteLine("2 - Pesquisar Aluno");
        Console.WriteLine("3 - Listar Alunos");
        Console.WriteLine("4 - Sair");
    }

    public static void Main (string[] args){

        List<Aluno> alunos = new List<Aluno>();


        int opcao;
        Menu();
        opcao = Int32.Parse(Console.ReadLine());

        Switch(opcao != 4){
            case 1:
                Aluno temp = new Aluno();
                Console.WriteLine("Digite o nome do aluno:");
                temp.nome = Console.ReadLine();
                Console.WriteLine("Digite a idade do aluno:");
                temp.idade = Int32.Parse(Console.ReadLine());
                Console.WriteLine("Digite a matricula do aluno:");
                temp.matricula = Console.ReadLine();
                alunos.Add(temp);
                break;
             case 2:
                Console.WriteLine("Digite a matricula do aluno:");
                int matricula = Int32.Parse(Console.ReadLine());
                if(alunos.Exists(item => item.Matricula == matricula)){
                    Console.WriteLine("Aluno cadastrado");
                    }else{
                        Console.WriteLine("Aluno não cadastrado");
                        }
                }
                break;
                case 3:
        }
    }
}

