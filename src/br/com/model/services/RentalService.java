package br.com.model.services;

import br.com.model.entity.CarRental;
import br.com.model.entity.Invoice;

public class RentalService {

	private Double pricePerHour;
	private Double pricePerDay;

	private TaxService taxService;

	public RentalService() {

	}

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public TaxService getTaxService() {
		return taxService;
	}

	public void setTaxService(BrazilTaxServices taxService) {
		this.taxService = taxService;
	}

	public void processInvoice(CarRental carRental) {

		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();

		double hours = (double) (t2 - t1) / 1000 / 60 / 60;

		double basicPayment;
		
		if (hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;
		}
		else {
			basicPayment = Math.ceil(hours/24) * pricePerDay;
		}
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}

	@Override
	public String toString() {
		return "RentalService [pricePerHour=" + pricePerHour + ", pricePerDay=" + pricePerDay + ", taxService="
				+ taxService + "]";
	}

}
