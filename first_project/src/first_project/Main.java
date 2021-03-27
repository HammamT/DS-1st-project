package first_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	static int students_total_num = 0;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Node head = new Node();
		Node curr = head;

		String row;
		BufferedReader csvReader = new BufferedReader(
				new FileReader("/home/hammam/eclipse-workspace/first_project/src/input_files/Gaza_2019.csv"));
		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(",");
			sorted_insert(head, Double.parseDouble(data[2]), Integer.parseInt(data[0]), data[1]);
			students_total_num++;
		}
		csvReader.close();
		// print_first_ten(head);
		// get_median(head);
		// get_mod(head);
		get_number_precentage(head, 90);

//		Node temp = head.next;
//		while (temp != null) {
//			System.out.println(temp.getGpa());
//			temp = temp.next;
//		}
	}

	public static boolean search_list(int seat_number, Node list) {
		Node temp = list;
		while (temp != null) {
			if (temp.getSeat_number() == seat_number)
				return true;
			temp = temp.next;
		}
		return false;
	}

	public static double calc_mean(Node header) {
		double sum = 0;
		Node temp = header;
		while (temp != null) {
			sum += temp.getGpa();
			temp = temp.next;
		}
		return sum / students_total_num;
	}

	public static double calc_variance(Node header) {
		double mean = calc_mean(header);
		Node temp = header.next;
		double sum_squared = 0;
		while (temp != null) {
			sum_squared += Math.pow((temp.getGpa() - mean), 2);
			temp = temp.next;
		}
		return sum_squared / students_total_num;
	}

	public static double calc_standard_div(Node header) {
		return Math.sqrt(calc_variance(header));
	}

	public static void sorted_insert(Node head, double gpa, int seat_number, String branch) {
		Node new_node = new Node(gpa, seat_number, branch);
		Node current;
		if (head.next == null || head.next.getGpa() <= new_node.getGpa()) { // list is empty or new gpa is less than the
																			// one in the front of the list => place new
																			// node at front
			new_node.next = head.next;
			head.next = new_node;
		} else { // fined appropriate position for the new node
			current = head.next;
			while (current.next != null && current.next.getGpa() > new_node.getGpa()) {
				current = current.next;
			}
			new_node.next = current.next;
			current.next = new_node;
		}
	}

	public static boolean delete_node(Node head, int seat_number) {
		Node temp = head.next, prev = null;
		if (temp == null || temp.getSeat_number() == seat_number) {
			head.next = temp;
			return true;
		}

		while (temp != null && temp.getSeat_number() != seat_number) {
			prev = temp;
			temp = temp.next;
		}

		if (temp == null)
			return false;

		prev.next = temp.next;
		return true;
	}

	public static Node search(Node head, int seat_number) {
		Node temp = head.next;
		while (temp != null) {
			if (temp.getSeat_number() == seat_number)
				return temp;
		}
		return null;
	}

	public static void print_first_ten(Node head) {
		int count = 0;
		Node temp = head.next;
		while (temp != null && temp.next != null && count < 10) {
			System.out.println(temp.getGpa() + " " + temp.getSeat_number());
			if (temp.getGpa() != temp.next.getGpa())
				count++;
			temp = temp.next;
		}
	}

	// works for sorted list
	public static void get_mod(Node head) {
		if (head.next == null)
			return;
		Node temp = head.next;
		double prev_gpa = temp.getGpa();
		double mod_number = temp.getGpa();
		temp = temp.next;
		int count = 1, max_count = 0;

		while (temp != null) {
			if (temp.getGpa() == prev_gpa) {
				count++;
			} else {
				if (count > max_count) {
					max_count = count;
					mod_number = prev_gpa;
				}
				count = 0;
			}
			prev_gpa = temp.getGpa();
			temp = temp.next;
		}
		System.out.println(mod_number + " " + max_count);
	}

	public static void get_median(Node head) {
		if (head.next == null)
			return;
		Node slow = head.next;
		Node fast = head.next;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		System.out.println(slow.getGpa() + " " + slow.getSeat_number());
	}

	public static void get_number_precentage(Node head, double gpa) {
		int count = 0;
		Node temp = head.next;
		while (temp != null) {
			if (temp.getGpa() >= gpa)
				count++;
			temp = temp.next;
		}
		System.out.println(count + " " + ((count*1.0) / students_total_num));
	}

}
