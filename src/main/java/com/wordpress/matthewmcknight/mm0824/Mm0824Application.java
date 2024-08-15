package com.wordpress.matthewmcknight.mm0824;

import com.wordpress.matthewmcknight.mm0824.model.RentalAgreement;
import com.wordpress.matthewmcknight.mm0824.model.Tool;
import com.wordpress.matthewmcknight.mm0824.model.ToolTypeInfo;
import io.jstach.jstachio.JStachio;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

@Log4j2
@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class Mm0824Application {

	public static void main(String[] args) {
		SpringApplication.run(Mm0824Application.class, args);
	}

	/**
	 * Parses all the command line options and builds & prints a rental agreement
	 * @param applicationConfiguration	application configuration
	 * @return							an ApplicatonRunner
	 */
	@Bean
	ApplicationRunner applicationRunner(ApplicationConfiguration applicationConfiguration) {
		return args -> {
			CommandLineParser parser = new DefaultParser();

			Pattern toolCodePattern = Pattern.compile("(^[A-Z]{4}$)", Pattern.CASE_INSENSITIVE);
			Option toolCodeOption = new Option("tc", "tool-code", true, String.format("Tool Code %s", toolCodePattern.pattern()));
			toolCodeOption.setRequired(true);
			Option rentalDateOption = new Option("dt", "rental-date", true, "Rental Date (yyyy-MM-dd)");
			rentalDateOption.setRequired(true);
			Option rentalDaysOption = new Option("dy", "rental-days", true, "Rental Days (0..100)");
			rentalDaysOption.setRequired(true);
			rentalDaysOption.setType(Integer.class);
			Option discountOption = new Option("di", "discount", true, "Percentage Discount (0..100)");
			discountOption.setRequired(false);
			discountOption.setType(Integer.class);
			Option helpOption = new Option("h", "help", false, "Print this message");
			Option listOption = new Option("l", "list", false, "List configuration");
			Options helpOptions = new Options();
			helpOptions.addOption(helpOption);
			Options listOptions = new Options();
			listOptions.addOption(listOption);

			Options options = new Options();
			options.addOption(toolCodeOption);
			options.addOption(rentalDateOption);
			options.addOption(rentalDaysOption);
			options.addOption(discountOption);
			options.addOption(listOption);
			options.addOption(helpOption);

			CommandLine line;
			LocalDate date = null;
			int days = 0;
			Tool tool = null;
			ToolTypeInfo info = null;
			int discount = 0;
			try {

				line = parser.parse(helpOptions, args.getSourceArgs(), true);
				if(line.hasOption(helpOption)) {
					printHelp(options, null);
					System.exit(1);
				}

				line = parser.parse(listOptions, args.getSourceArgs(), true);
				if(line.hasOption(listOption)) {
					System.out.println("Loaded Tools");
					applicationConfiguration.getTools().forEach(System.out::println);
					System.out.println("Loaded Tool Info");
					applicationConfiguration.getToolInfo().forEach(System.out::println);
					System.out.println("Loaded Holidays");
					applicationConfiguration.getHolidays().forEach(System.out::println);
					System.exit(1);
				}
				line = parser.parse(options, args.getSourceArgs(), false);
				String toolCodeString = line.getParsedOptionValue(toolCodeOption);
				if(!toolCodePattern.matcher(toolCodeString).matches()) {
					throw new ParseException(String.format("Tool Code wrong format, was %s must be %s", toolCodeString, toolCodePattern.pattern()));
				}
				tool = RentalAgreementTools.lookupTool(applicationConfiguration, toolCodeString);
				info = RentalAgreementTools.lookupToolType(applicationConfiguration, tool);

				String stringDate = line.getParsedOptionValue(rentalDateOption);
				try {
					date = LocalDate.parse(stringDate);
				} catch (DateTimeParseException ex) {
					throw new ParseException(String.format("Tool Date wrong format %s must be %s", ex.getMessage(), "YYYY-MM-dd"));
				}

				days = line.getParsedOptionValue(rentalDaysOption);
				if(days < 0 || days > 100) {
					throw new ParseException("Rental Days is out of range (0-100)");
				}

				if(line.hasOption(discountOption)) {
					discount = line.getParsedOptionValue(discountOption);
					if (discount < 0 || discount > 100) {
						throw new ParseException("Discount percentage is out of range (0-100)");
					}
				}
			} catch (Exception ex) {
				printHelp(options, ex.getMessage());
				System.exit(0);
			}

			RentalAgreement ra = RentalAgreementTools.calculateRentalAgreement(
					applicationConfiguration.getHolidays(),
					tool,
					info,
					date,
					days,
					discount);
			StringBuilder builder = new StringBuilder();
			JStachio.render(ra, builder);
			System.out.println(builder);

			System.exit(1);
		};
	}

	private void printHelp(@NonNull Options options, @Nullable String message) {
		if(message != null) {
			System.out.printf("Could not parse arguments: %s%n", message);
		}
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(String.format("java -jar mm0824-%s.jar", this.getClass().getPackage().getImplementationVersion()), options);
	}
}


