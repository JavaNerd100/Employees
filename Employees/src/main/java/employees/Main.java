package employees;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        int totalSalaries = getTotalSalaries();
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        System.out.printf("The total payout for employees %s%n",numberFormat.format(totalSalaries));

    }

    private static int getTotalSalaries() {
        String people = """
                Flinstone, Fred, 1/1/1900,Programmer,{lopcd=2000,yoe=10,iq=140}
                Flinstone2, Fred, 1/1/1900,Programmer,{lopcd=1300,yoe=14,iq=100}
                Flinstone3, Fred, 1/1/1900,Programmer,{lopcd=2300,yoe=8,iq=105}
                Flinstone4, Fred, 1/1/1900,Programmer,{lopcd=1630,yoe=3,iq=115}
                Flinstone5, Fred, 1/1/1900,Programmer,{lopcd=5,yoe=10,iq=100}
                Rubble, Barney, 2/2/1905,Manager,{orgSize=300,dr=10}
                Rubble2, Barney, 2/2/1905,Manager,{orgSize=100,dr=4}
                Rubble3, Barney, 2/2/1905,Manager,{orgSize=200,dr=2}
                Rubble4, Barney, 2/2/1905,Manager,{orgSize=500,dr=8}
                Rubble5, Barney, 2/2/1905,Manager,{orgSize=175,dr=12}
                Flinstone, Wilma, 3/3/1910,Analyst,{projectCount=3}
                Flinstone2, Wilma, 3/3/1910,Analyst,{projectCount=4}
                Flinstone3, Wilma, 3/3/1910,Analyst,{projectCount=5}
                Flinstone4, Wilma, 3/3/1910,Analyst,{projectCount=6}
                Flinstone5, Wilma, 3/3/1910,Analyst,{projectCount=9}
                Rubble, Betty, 4/4/1915,CEO,{avgStockPrice=300}
                """;

        String peopleRegex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
        Pattern peoplePat = Pattern.compile(peopleRegex);
        Matcher peopleMat = peoplePat.matcher(people);


        String programmerDetailsRegex = "\\w+\\=(?<lopcd>\\w+),\\w+\\=(?<yoe>\\w+),\\w+\\=(?<iq>\\w+)";
        Pattern programmerPat = Pattern.compile(programmerDetailsRegex);


        String managerDetailsRegex = "\\w+\\=(?<orgSize>\\w+),\\w+\\=(?<dr>\\w+)";
        Pattern managerPat = Pattern.compile(managerDetailsRegex);

        String analystDetailsRegex = "\\w+\\=(?<projectCount>\\w+)";
        Pattern analystPat = Pattern.compile(analystDetailsRegex);

        String CEORegex = "\\w+\\=(?<avgStockPrice>\\w+)";
        Pattern CEOPat = Pattern.compile(CEORegex);


        int totalSalaries = 0;
        while (peopleMat.find()) {
            totalSalaries += switch (peopleMat.group("role")){
                case "Programmer" -> {
                    String details = peopleMat.group("details");
                    Matcher programmerMat = programmerPat.matcher(details);
                    int salary =0;
                    if (programmerMat.find()) {
                        int lopcd = Integer.parseInt(programmerMat.group("lopcd"));
                        int yoe = Integer.parseInt(programmerMat.group("yoe"));
                        int iq = Integer.parseInt(programmerMat.group("iq"));
                        salary = 3000 + lopcd * yoe * iq;
                    } else {
                        salary = 3000;
                    }
                    String lastName = peopleMat.group("lastName");
                    String firstName = peopleMat.group("firstName");
                    System.out.printf("%s, %s: %s%n",lastName,firstName,NumberFormat.getCurrencyInstance().format(salary));
                    yield salary;

                }
                case "Manager" -> {
                    String details = peopleMat.group("details");
                    Matcher managerMat = managerPat.matcher(details);
                    int salary =0;
                    if (managerMat.find()) {
                        int orgSize = Integer.parseInt(managerMat.group("orgSize"));
                        int dr = Integer.parseInt(managerMat.group("dr"));
                        salary = 3500 +  orgSize * dr;
                    } else {
                        salary = 3500;
                    }

                    String lastName = peopleMat.group("lastName");
                    String firstName = peopleMat.group("firstName");
                    System.out.printf("%s, %s: %s%n",lastName,firstName,NumberFormat.getCurrencyInstance().format(salary));
                    yield salary;

                }
                case "Analyst" -> {
                    String details = peopleMat.group("details");
                    Matcher analystMat = analystPat.matcher(details);
                    int salary =0;
                    if (analystMat.find()) {
                        int projectCount = Integer.parseInt(analystMat.group("projectCount"));
                        salary = 2500 +  projectCount * 2;
                    } else {
                        salary = 2500;
                    }
                    String lastName = peopleMat.group("lastName");
                    String firstName = peopleMat.group("firstName");
                    System.out.printf("%s, %s: %s%n",lastName,firstName,NumberFormat.getCurrencyInstance().format(salary));
                    yield salary;

                }
                case "CEO" ->{
                    String details = peopleMat.group("details");
                    Matcher CEOMat = CEOPat.matcher(details);
                    int salary =0;
                    if (CEOMat.find()) {
                        int avgStockPrice = Integer.parseInt(CEOMat.group("avgStockPrice"));
                        salary = 5000 * avgStockPrice;
                    } else {
                        salary = 5000;
                    }

                    String lastName = peopleMat.group("lastName");
                    String firstName = peopleMat.group("firstName");
                    System.out.printf("%s, %s: %s%n",lastName,firstName,NumberFormat.getCurrencyInstance().format(salary));
                    yield salary;

                }
               default -> 0;
            };
        }
        return totalSalaries;
    }
}