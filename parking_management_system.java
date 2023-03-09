// PARKING MANAGEMENT SYSTEM

// INPUT: CHECKIN GJ03DF-2708 08:15am NR
// OUTPUT:A-2
// INPUT: CHECKIN GJT-3293 09:00am R
// OUTPUT:A-1
// INPUT:CHECKOUT GJ03DF-2708 11:45am
// OUTPUT:80
// INPUT:CHECKOUT GJT-3293 12:00pm
// OUTPUR:80
// INPUT:CHECKIN GJ09DF-9687 12:15pm NR
// OUTPUT:A-2
// INPUT:CHECKOUT GJ09DF-9687 12:45pm
// OUTPUT:50
// INPUT:GENERATE REPORT
// OUTPUT:

// PARKING SLOT, CAR NO, CHECK IN TIME, CHECK OUT TIME, CHARGES, CATEGORY
// A-1, GJT-3293, 09:00am, 12:00pm 80, R
// A-2, GJ03DF-2708, 08:15am, 11:45am, 80, NR
// A-2, GJ09DF-9687, 12:15pm, 12:45pm, 50, NR



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

//Dev Bhayani
class parking_management_system {
    public static void print(int arr[][]) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    public static void print(char arr[][]) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    public static int calculate_charges(String in, String out) {
        int charges = 0;
        // System.out.println(in);
        // System.out.println(out);
        int hour_in = Integer.parseInt(in.substring(0, 2));
        int min_in = Integer.parseInt(in.substring(3, 5));
        int hour_out = Integer.parseInt(out.substring(0, 2));
        int min_out = Integer.parseInt(out.substring(3, 5));
        int inTime = 0;
        int outTime = 0;
        int duration = 0;
        if (in.substring(5).equals(out.substring(5))) {
            if (hour_in > hour_out) {
                charges = 100;
            } else if (hour_in == hour_out && min_in > min_out) {
                charges = 100;
            } else {
                inTime = ((hour_in * 60) + min_in);// 495
                outTime = ((hour_out * 60) + min_out);// 705
                duration = outTime - inTime;
                // System.out.println(duration);
                if (duration <= 120) {
                    charges = 50;
                } else if (duration > 120 && duration <= 240) {
                    charges = 80;
                } else {
                    charges = 100;
                }
            }
        } else {
            duration = 60 - min_in;
            duration += ((12 - hour_in - 1) * 60);
            duration += (((12 - hour_out) * 60) + min_out);
            if (duration <= 120) {
                charges = 50;
            } else if (duration > 120 && duration <= 240) {
                charges = 80;
            } else {
                charges = 100;
            }
        }
        return charges;
    }

    public static String checkIn(String[] arrOfStr, char[][] parking, int[][] curr_parking) {
        String place = "";
        // Creating matrix where each entry is 0 and 1
        // 0 represents car parked at that slot slot is full
        // 1 represents still not filled yet.
        if (arrOfStr[3].equals("NR")) {
            for (int i = 0; i < parking.length; i++) {
                for (int j = 0; j < parking[0].length; j++) {
                    if (parking[i][j] == 'N' && curr_parking[i][j] == 0) {
                        place += (char) (i + 65);
                        place += "-";
                        place += String.valueOf(j+1);
                        curr_parking[i][j] = 1;
                        print(curr_parking);

                        return place;
                    }

                }
            }
        } else if (arrOfStr[3].equals("R")) {
            for (int i = 0; i < parking.length; i++) {
                for (int j = 0; j < parking[0].length; j++) {
                    if (parking[i][j] == 'R' && curr_parking[i][j] == 0) {
                        place += (char) (i + 65);
                        place += "-";
                        place += String.valueOf(j+1);
                        curr_parking[i][j] = 1;
                        print(curr_parking);
                        
                        return place;
                    }
                }
            }
            for (int i = 0; i < parking.length; i++) {
                for (int j = 0; j < parking[0].length; j++) {
                    if (parking[i][j] == 'N' && curr_parking[i][j] == 0) {
                        place += (char) (i + 65);
                        place += "-";
                        place += (char) (j + '0' + 1);
                        curr_parking[i][j] = 1;
                        print(curr_parking);
                        
                        return place;
                    }
                }
            }

        }
        return "PARKING FULL";
    }

    public static void checkOut(String[] arrOfStr, char[][] parking, int[][] curr_parking, String place) {
        int i = place.charAt(0) - 'A';
        int j = Integer.parseInt(place.substring(2, place.length())) - 1;
        curr_parking[i][j] = 0;
        print(curr_parking);
        
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int floor = sc.nextInt();
        int f_park = sc.nextInt();
        int total_park = floor * f_park;
        int reserved_total = 10;
        String[] reserved = new String[reserved_total];
        for (int i = 0; i < reserved_total; i++) {
            reserved[i] = sc.next();
        }
        
        char[][] parking = new char[floor][f_park];
        int[][] curr_parking = new int[floor][f_park];
        // Creating matrix parking where each entry is N and R
        // N represents non reserved slots are not filled yet
        // 1Rrepresents reserved slots are not filled yet
    

        for (int i = 0; i < floor; i++) {
            for (int j = 0; j < f_park; j++) {
                parking[i][j] = 'N';
                curr_parking[i][j] = 0;
            }
        }
        for (int i = 0; i < reserved_total; i++) {
            String s = reserved[i].substring(2, reserved[i].length());
            parking[reserved[i].charAt(0) - 65][Integer.parseInt(s) - 1] = 'R';
        }
        print(parking);
        
        ArrayList<String> slot = new ArrayList<>();
        ArrayList<String> car_no = new ArrayList<>();
        ArrayList<String> in_time = new ArrayList<>();
        ArrayList<String> out_time = new ArrayList<>();
        ArrayList<Integer> charges = new ArrayList<>();
        ArrayList<String> category = new ArrayList<>();
        ArrayList<String> report = new ArrayList<>();
        String s = "";

        s += sc.nextLine();
        do {
            s = "";
            s += sc.nextLine();
            if (s.contains("CHECKIN")) {
                String[] arrOfStr = s.split(" ");
                if (!car_no.contains(arrOfStr[1])) {
                    String ans = checkIn(arrOfStr, parking, curr_parking);
                    System.out.println(ans);
                    if (!ans.equals("PARKING FULL")) {
                        car_no.add(arrOfStr[1]);
                        in_time.add(arrOfStr[2]);
                        out_time.add("");
                        category.add(arrOfStr[3]);
                        charges.add(0);
                        slot.add(ans);
                    }
                } else {
                    System.out.println("This car is already in parking");
                }

            } else if (s.contains("CHECKOUT")) {
                // System.out.println(s);
                String[] arrOfStr = s.split(" ");
                if (car_no.contains(arrOfStr[1])) {
                    int index = car_no.indexOf(arrOfStr[1]);
                    String place = slot.get(index);
                    out_time.add(index, arrOfStr[2]);
                    int in = 0;
                    int out = 0;
                    checkOut(arrOfStr, parking, curr_parking, place);
                    charges.set(index, calculate_charges(in_time.get(index), out_time.get(index)));
                    System.out.println(charges.get(index));
                    String ans = slot.get(index) + " " + in_time.get(index) + " " + car_no.get(index) + " "
                            + out_time.get(index) + " " + charges.get(index).toString() + " " + category.get(index);

                    report.add(ans);
                    car_no.remove(index);
                    in_time.remove(index);
                    out_time.remove(index);
                    charges.remove(index);
                    slot.remove(index);
                    category.remove(index);
                } else {
                    System.out.println("NO CAR AVAILABLE OF THAT NUMBER");
                }
            }
        } while (!s.equals("GENERATE REPORT"));
        System.out.println("PARKING SLOT, CAR NO, CHECK IN TIME, CHECK OUT TIME, CHARGES, CATEGORY");
        Collections.sort(report);
        for (int i = 0; i < report.size(); i++) {
            String[] ans = report.get(i).split(" ");
            System.out.println(ans[0] + ", " + ans[2] + ", " + ans[1] + ", " + ans[3] + ", " + ans[4] + ", " + ans[5]);
        }
        sc.close();
    }
}