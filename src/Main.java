import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
            makeRecord();

    }

    public static void makeRecord() throws Exception{
        System.out.println("Введите следующие данные в произвольном порядке, разделенные пробелом: Фамилия, Имя, " +
                "Отчество, дата рождения, номер телефона, пол");
        String input;
        try(Scanner scan = new Scanner(System.in)){
            input = scan.nextLine();
        }catch(Exception e){
            throw new RuntimeException("Ошибка в работе с консолью");
        }
        String[] words = input.split(" ");
        if(words.length == 6){
            String surname = words[0];
            String name = words[1];
            String patronymic = words[2];
            SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
            Date birthdate;
            try{
                birthdate = format.parse(words[3]);
            }catch (ParseException e){
                throw new ParseException("Неверный формат даты", e.getErrorOffset());
            }
            long number;
            try{
                number = Long.parseLong(words[4]);
            }catch (NumberFormatException e){
                throw new NumberFormatException("Неверный формат телефона");
            }
            String sex = words[5];
            if (!sex.toLowerCase().equals("m") && !sex.toLowerCase().equals("f")){
                throw new RuntimeException("Неверно введен пол");
            }
            String filename = surname.toLowerCase() + ".txt";
            File file = new File(filename);
            try(FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(String.format("%s,%s,%s,%s,%s,%s", surname, name, patronymic, format.format(birthdate),
                        number, sex));
            }catch(IOException e){
                throw new FileSystemException("Ошибка при работе с файлом!");
            }
        } else if (words.length < 6) {
            throw new RuntimeException("Вы ввели меньше параметров!");
        }else {
            throw new RuntimeException("Вы ввели лишнее!");
        }
    }
}