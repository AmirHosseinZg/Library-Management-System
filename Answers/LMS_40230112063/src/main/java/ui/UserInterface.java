package ui;

import basic.classes.Library;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInterface {

    Library library = new Library("AmirZg", 500, "7am to 11pm");
    Scanner input = new Scanner(System.in);
    String operation;
    private String signed_in_name, signed_in_phone_number;


    public void printLibrarySupportedCommand() {
        System.out.println("lib add book <title> <author> <description>"); //add a new book to the library
        System.out.println("lib get hrs");//retrieve library operating hours
        System.out.println("lib rent <bookName> <author>");//rent a book for a specific member
        System.out.println("lib get available books");//view available books for rental
        System.out.println("lib remove member <user-name> <phone_number>");//remove a member from the library(admin privilege required)
        System.out.println("lib return <bookName> <author>");//return a rented book to the library
        System.out.println("lib remove book <title> <author>"); //remove a book from the library(admin privilege required)
        System.out.println("lib PortalPass");
        System.out.println("Exit");
        System.out.println("*** Be sure to use the <> operator to send arguments in the command line ***");
    }


    public void printPortalPassSupportedCommand() {
        System.out.println("sign in as normal user <name> <phone-number>");
        System.out.println("sign in as admin <name> <phone-number> <password>");
        System.out.println("creating new normal user account <name> <phone-number>");
        System.out.println("add new super doer ( admin ) <name> <phone-number> <password> " +
                "(attention :the password must have at least 8 characters)"); //admin access required
        System.out.println("*** Be sure to use the <> operator to send arguments in the command line ***");
    }


    public void libraryPortalPass() {
        // PortalPass
        // Sign in : logging in with existing account, Sign up : creating new account

        System.out.println("Welcome to library PortalPass");

        String admin_password;

        while (true) {
            System.out.print("Enter your desired command : ");
            System.out.println("notice : To see list of supported command use the following instruction :");
            System.out.println("PortalPass --help");
            System.out.print(">>");
            operation = input.nextLine();

            final Pattern portalpass_sign_in_user_pattern = Pattern.compile("^sign\\s+in\\s+as\\s+normal\\s+user\\s+<([a-zA-Z]+(?:\\s[a-zA-Z]+)*)>\\s+<((9[0-9]{9})|(09[0-9]{9}))>$", Pattern.CASE_INSENSITIVE);
            final Matcher portalpass_sign_in_user_matcher = portalpass_sign_in_user_pattern.matcher(operation);

            final Pattern portalpass_sign_in_admin_pattern = Pattern.compile("^sign\\s+in\\s+as\\s+admin\\s+<([a-zA-Z]+(?:\\s[a-zA-Z]+)*)>\\s+<((9[0-9]{9})|(09[0-9]{9}))>\\s+<([^ ]{8,})>$", Pattern.CASE_INSENSITIVE);
            final Matcher portalpass_sign_in_admin_matcher = portalpass_sign_in_admin_pattern.matcher(operation);

            final Pattern portalpass_new_user_account_pattern = Pattern.compile("^creating\\s+new\\s+normal\\s+user\\s+account\\s+<([a-zA-Z]+(?:\\s[a-zA-Z]+)*)>\\s+<((9[0-9]{9})|(09[0-9]{9}))>$", Pattern.CASE_INSENSITIVE);
            final Matcher portalpass_new_user_account_matcher = portalpass_new_user_account_pattern.matcher(operation);

            final Pattern portalpass_add_admin_pattern = Pattern.compile("^add\\s+new\\s+super\\s+doer\\s+<([a-zA-Z]+(?:\\s[a-zA-Z]+)*)>\\s+<((9[0-9]{9})|(09[0-9]{9}))>\\s+<([^ ]{8,})>$", Pattern.CASE_INSENSITIVE);
            final Matcher portalpass_add_admin_matcher = portalpass_add_admin_pattern.matcher(operation);

            final Pattern portalpass_help_pattern = Pattern.compile("^PortalPass\\s+--help$", Pattern.CASE_INSENSITIVE);
            final Matcher portalpass_help_matcher = portalpass_help_pattern.matcher(operation);

            if (portalpass_help_matcher.find()) {
                printPortalPassSupportedCommand();
                System.out.println("--------------------------------------------------------------");
            } else if (portalpass_sign_in_user_matcher.find()) {
                signed_in_name = portalpass_sign_in_user_matcher.group(1);
                signed_in_phone_number = portalpass_sign_in_user_matcher.group(2);
                if (library.memberExistenceChecker(signed_in_name, signed_in_phone_number) != null) {
                    System.out.println("You signed in successfully as normal user with name and phone number :  " + signed_in_name + "," + signed_in_phone_number);
                    System.out.println("--------------------------------------------------------------");
                    break;
                } else {
                    System.out.println("There is no user with the given information in registered user list .");
                    System.out.println("--------------------------------------------------------------");
                }
            } else if (portalpass_sign_in_admin_matcher.find()) {
                signed_in_name = portalpass_sign_in_admin_matcher.group(1);
                signed_in_phone_number = portalpass_sign_in_admin_matcher.group(2);
                admin_password = portalpass_sign_in_admin_matcher.group(3);
                if (library.adminExistenceChecker(signed_in_name, signed_in_phone_number, admin_password) != null) {
                    System.out.println("You signed in successfully as admin with name and phone number : " + signed_in_name + "," + signed_in_phone_number);
                    System.out.println("--------------------------------------------------------------");
                    break;
                } else {
                    System.out.println("There is no admin with the given information in registered admin list .");
                    System.out.println("--------------------------------------------------------------");
                }

            } else if (portalpass_new_user_account_matcher.find()) {
                signed_in_name = portalpass_new_user_account_matcher.group(1);
                signed_in_phone_number = portalpass_new_user_account_matcher.group(2);
                library.addMember(signed_in_name, signed_in_phone_number);
                System.out.println("A new normal user account has been created and you have successfully logged in with name and phone number : " + signed_in_name + "," + signed_in_phone_number);
                System.out.println("--------------------------------------------------------------");
                break;
            } else if (portalpass_add_admin_matcher.find()) {
                System.out.println("adding new admin for library management require  admin password . enter password : \n>>");
                if (library.passwordExistenceChecker(input.nextLine())) {
                    signed_in_name = portalpass_add_admin_matcher.group(1);
                    signed_in_phone_number = portalpass_add_admin_matcher.group(2);
                    admin_password = portalpass_add_admin_matcher.group(3);
                    library.addAmin(signed_in_name, signed_in_phone_number, admin_password);
                    System.out.println("A new super doer account has been created and you have successfully logged in with name and phone number : " + signed_in_name + "," + signed_in_phone_number);
                    System.out.println("--------------------------------------------------------------");
                    break;
                } else {
                    System.out.println("Access denied , Wrong admin password");
                }

            } else {
                System.out.println("invalid command . . . try again");
                System.out.println("Keep in mind that this error may be caused by entering the wrong command or entering the name," +
                        " mobile number or password in the wrong form.");
                System.out.println("--------------------------------------------------------------");
            }
        }
    }


    public void libraryLobby() {

        library.dataRetriever(); // Retrieve all data from database

        libraryPortalPass();

        // Library System
        System.out.println("Welcome to " + library.getLibraryName() + " Library Management System");


        while (true) {
            System.out.print("Enter your desired command : ");
            System.out.println("notice : To see list of supported command use the following instruction :");
            System.out.println("Library System --help");
            System.out.print(">>");
            operation = input.nextLine();

            final Pattern lms_add_book_pattern = Pattern.compile("^lib\\s+add\\s+book\\s+<([0-9]*\\s*[a-zA-Z]+(?:[0-9]*\\s*[a-zA-Z]*)*)>\\s+<([a-zA-Z]+(?:\\s[a-zA-Z]+)*)>\\s+<([0-9]*\\s*[a-zA-Z]+(?:[0-9]*\\s*[a-zA-Z]*)*)>$", Pattern.CASE_INSENSITIVE);
            final Matcher lms_add_book_matcher = lms_add_book_pattern.matcher(operation);

            final Pattern lms_get_operating_hours_pattern = Pattern.compile("^lib\\s+get\\s+hrs$", Pattern.CASE_INSENSITIVE);
            final Matcher lms_get_operating_hours_matcher = lms_get_operating_hours_pattern.matcher(operation);

            final Pattern lms_rent_book_pattern = Pattern.compile("^lib\\s+rent\\s+<([0-9]*\\s*[a-zA-Z]+(?:[0-9]*\\s*[a-zA-Z]*)*)>\\s+<([a-zA-Z]+(?:\\s[a-zA-Z]+)*)>$", Pattern.CASE_INSENSITIVE);
            final Matcher lms_rent_book_matcher = lms_rent_book_pattern.matcher(operation);

            final Pattern lms_available_book_pattern = Pattern.compile("^lib\\s+get\\s+available\\s+books$", Pattern.CASE_INSENSITIVE);
            final Matcher lms_available_book_matcher = lms_available_book_pattern.matcher(operation);

            final Pattern lms_remove_member_pattern = Pattern.compile("^lib\\s+remove\\s+member\\s+<([a-zA-Z]+(?:\\s[a-zA-Z]+)*)>\\s+<((9[0-9]{9})|(09[0-9]{9}))>$", Pattern.CASE_INSENSITIVE);
            final Matcher lms_remove_member_matcher = lms_remove_member_pattern.matcher(operation);

            final Pattern lms_return_book_pattern = Pattern.compile("^lib\\s+return\\s+<([0-9]*\\s*[a-zA-Z]+(?:[0-9]*\\s*[a-zA-Z]*)*)>\\s+<([a-zA-Z]+(?:\\s[a-zA-Z]+)*)>$", Pattern.CASE_INSENSITIVE);
            final Matcher lms_return_book_matcher = lms_return_book_pattern.matcher(operation);

            final Pattern lms_remove_book_pattern = Pattern.compile("^lib\\s+remove\\s+book\\s+<([0-9]*\\s*[a-zA-Z]+(?:[0-9]*\\s*[a-zA-Z]*)*)>\\s+<([a-zA-Z]+(?:\\s[a-zA-Z]+)*)>$", Pattern.CASE_INSENSITIVE);
            final Matcher lms_remove_book_matcher = lms_remove_book_pattern.matcher(operation);

            final Pattern lms_goPortalPass_pattern = Pattern.compile("^lib\\s+remove\\s+book\\s+<([0-9]*\\s*[a-zA-Z]+(?:[0-9]*\\s*[a-zA-Z]*)*)>\\s+<([a-zA-Z]+(?:\\s[a-zA-Z]+)*)>$", Pattern.CASE_INSENSITIVE);
            final Matcher lms_goPortalPass_matcher = lms_goPortalPass_pattern.matcher(operation);

            final Pattern lms_help_pattern = Pattern.compile("^Library\\s+System\\s+--help$", Pattern.CASE_INSENSITIVE);
            final Matcher lms_help_matcher = lms_help_pattern.matcher(operation);

            final Pattern lms_exit_pattern = Pattern.compile("^Exit", Pattern.CASE_INSENSITIVE);
            final Matcher lms_exit_matcher = lms_exit_pattern.matcher(operation);


            if (lms_help_matcher.find()) {
                printLibrarySupportedCommand();
                System.out.println("--------------------------------------------------------------");
            } else if (lms_add_book_matcher.find()) {
                String title = lms_add_book_matcher.group(1);
                String author = lms_add_book_matcher.group(2);
                String description = lms_add_book_matcher.group(3);
                library.addBook(title, author, description);
                System.out.println("--------------------------------------------------------------");
            } else if (lms_get_operating_hours_matcher.find()) {
                System.out.println(library.getOperating_hours());
                System.out.println("--------------------------------------------------------------");
            } else if (lms_rent_book_matcher.find()) {
                String book_name = lms_rent_book_matcher.group(1);
                String author = lms_rent_book_matcher.group(2);
                library.rentBook(book_name, author, signed_in_name, signed_in_phone_number);
                System.out.println("--------------------------------------------------------------");
            } else if (lms_available_book_matcher.find()) {
                library.printAvailableBooks();
                System.out.println("--------------------------------------------------------------");
            } else if (lms_remove_member_matcher.find()) {
                String user_name = lms_remove_member_matcher.group(1);
                String phone_number = lms_remove_member_matcher.group(2);
                System.out.println("Removing users from the library require admin password . enter password : \n>>");
                if (library.passwordExistenceChecker(input.nextLine())) {
                    library.rmMember(user_name, phone_number);
                    System.out.println("--------------------------------------------------------------");
                } else {
                    System.out.println("Access denied , Wrong admin password");
                    System.out.println("--------------------------------------------------------------");
                }
            } else if (lms_return_book_matcher.find()) {
                String book_name = lms_return_book_matcher.group(1);
                String author = lms_return_book_matcher.group(2);
                library.returnBook(book_name, author, signed_in_name, signed_in_phone_number);
                System.out.println("--------------------------------------------------------------");
            } else if (lms_remove_book_matcher.find()) {
                String title = lms_remove_book_matcher.group(1);
                String author = lms_remove_book_matcher.group(2);
                System.out.println("Removing books from the book repository require admin password . enter password : \n>>");
                if (library.passwordExistenceChecker(input.nextLine())) {
                    library.rmBook(title, author);
                    System.out.println("--------------------------------------------------------------");
                } else {
                    System.out.println("Access denied , Wrong admin password");
                    System.out.println("--------------------------------------------------------------");
                }
            } else if (lms_goPortalPass_matcher.find()) {
                System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
                libraryPortalPass();
                libraryLobby();
            } else if (lms_exit_matcher.find()) {
                System.out.println("\n\n\n");
                System.out.println("Good luck ");
                System.out.println("Hope to see you again \n\r Bye :D");
                System.exit(0);
            } else {
                System.out.println("invalid command . . . try again");
                System.out.println("Keep in mind that this error may be caused by entering the wrong command or entering the name," +
                        " mobile number or password in the wrong form.");
                System.out.println("--------------------------------------------------------------");
            }
        }

    }
}
