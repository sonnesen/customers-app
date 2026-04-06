package com.sonnesen.customerservice.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.sonnesen.customerservice", importOptions = {
                com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests.class,
                com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeJars.class
})
public class HexagonalArchitectureTest {

        @ArchTest
        static final ArchRule domain_should_not_depend_on_frameworks_or_external_layers = noClasses()
                        .that().resideInAPackage("..domain..")
                        .should().dependOnClassesThat()
                        .resideInAnyPackage("..application..", "..infrastructure..", "..adapters..", "..controllers..",
                                        "..config..", "org.springframework..", "jakarta..", "javax..",
                                        "jakarta.persistence..",
                                        "javax.persistence..", "jakarta.transaction..", "javax.transaction..",
                                        "org.hibernate..",
                                        "org.mapstruct..", "lombok..");

        @ArchTest
        static final ArchRule application_nao_depende_de_adapters = noClasses()
                        .that().resideInAPackage("..application..")
                        .should().dependOnClassesThat()
                        .resideInAPackage("..adapters..");

        @ArchTest
        static final ArchRule inbound_nao_depende_de_outbound = noClasses()
                        .that().resideInAPackage("..adapters.inbound..")
                        .should().dependOnClassesThat()
                        .resideInAPackage("..adapters.outbound..");
}
