package seedu.menion.parser;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.menion.logic.commands.ListCommand;
import seedu.menion.logic.parser.ListParser;

public class ListParserTest {

	@Test
	public void checkBlankArgumentsShouldReturnListBlank() {

		Set<String> argumentsToList = new HashSet<String>();
		String args = "";
		
		assertEquals(ListCommand.LIST_BLANK, ListParser.checkListType(args, argumentsToList));
	}
	
	@Test
	public void checkListAllShouldReturnListAll(){
		
		Set<String> argumentsToList = new HashSet<String>();
		String args = "all";
		
		assertEquals(ListCommand.LIST_ALL, ListParser.checkListType(args, argumentsToList));
	}
	
	@Test
	public void checkListDateShouldReturnListDate(){
		
		Set<String> argumentsToList = new HashSet<String>();
		String args = "18-08-2016";
		
		assertEquals(ListCommand.LIST_DATE, ListParser.checkListType(args, argumentsToList));
	}
	
	@Test
	public void checkListMonthShouldReturnListMonth(){
		
		Set<String> argumentsToList = new HashSet<String>();
		String args = "october";
		
		assertEquals(ListCommand.LIST_MONTH, ListParser.checkListType(args, argumentsToList));
	}

	@Test
	public void checkListCompletedShouldReturnListCompleted(){
		
		Set<String> argumentsToList = new HashSet<String>();
		String args = "completed";
		
		assertEquals(ListCommand.LIST_COMPLETED, ListParser.checkListType(args, argumentsToList));
	}
	
	@Test
	public void checkWrongListArgumentShouldReturnWrongArgument(){
		
		Set<String> argumentsToList = new HashSet<String>();
		String args = "wrongargs";
		
		assertEquals(ListCommand.WRONG_ARGUMENT, ListParser.checkListType(args, argumentsToList));
	}
}
