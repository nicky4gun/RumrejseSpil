import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        boolean running = true;
           while (running) {
               try{
               Scanner scanner = new Scanner(System.in);
               System.out.println("Hello Captain looks at your name sign:");
               String name = scanner.nextLine();
                 if (name.isEmpty())
                 { System.out.println("huh must have been the wind ");
                     throw new IllegalArgumentException();}

                   System.out.println("the name you picked was " + name);
               System.out.println("ah i see your name is " + name + " since you are the captain you need to name our ship:");
               String ship = scanner.nextLine();
               if (ship.isEmpty())
                   {  System.out.println("you are not the captain");
                       throw new IllegalArgumentException();
                   }

                   System.out.println("the name you picked was " + ship);
               System.out.println("good name captain " + name + " the name " + ship + " is a good name; ");
               } catch (IllegalArgumentException e ) {
                   System.out.println("name cant be null");

               }


               }
           while (running) {

             try {
                 switch() {
                     case 1:{

                     } break;
                     case 2: {

                     } break;

                 }

             } catch (Exception e) {
                 System.out.println(" just for now");
             }

             }
           }
        }




