package com.kyanite.chatsubadmin;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.kyanite.chatsubadmin");

        noClasses()
            .that()
            .resideInAnyPackage("com.kyanite.chatsubadmin.service..")
            .or()
            .resideInAnyPackage("com.kyanite.chatsubadmin.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.kyanite.chatsubadmin.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
