# Calculating loan rates

## Overview

There is a need for a rate calculation system allowing prospective borrowers to
obtain a quote from our pool of lenders for 36 month loans. This system will 
take the form of a command-line application.

You will be provided with a file containing a list of all the offers being made
by the lenders within the system in CSV format, see the example market.csv file
provided alongside this specification.

You should strive to provide as low a rate to the borrower as is possible to
ensure that quotes are as competitive as they can be against our
competitors'. You should also provide the borrower with the details of the
monthly repayment amount and the total repayment amount.

Repayment amounts should be displayed to 2 decimal places and the rate of the 
loan should be displayed to one decimal place.

Borrowers should be able to request a loan of any £100 increment between £1000
and £15000 inclusive. If the market does not have sufficient offers from
lenders to satisfy the loan then the system should inform the borrower that it
is not possible to provide a quote at that time.

The application should take arguments in the form:

    cmd> [application] [market_file] [loan_amount]

Example:

    cmd> quote.exe market.csv 1500

The application should produce output in the form:

    cmd> [application] [market_file] [loan_amount]
    Requested amount: £XXXX
    Rate: X.X%
    Monthly repayment: £XXXX.XX
    Total repayment: £XXXX.XX

Example:

    cmd> quote.exe market.csv 1000
    Requested amount: £1000
    Rate: 7.0%
    Monthly repayment: £30.78
    Total repayment: £1108.10
 
## Implementation details

This has been set up as Spring Boot project using Java 8 features.  The application is built with Maven.

## Usage

```
> mvn package
> java -jar target\ratecalculator-0.0.1-SNAPSHOT.jar src/test/data/market-good-data.csv 1000
Requested amount: £1000
Rate: 7.77%
Monthly repayment: £34.25
Total repayment: £1233.08
```
 
