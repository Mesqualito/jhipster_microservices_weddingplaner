package rocks.gebsattel.hochzeit.cucumber.stepdefs;

import rocks.gebsattel.hochzeit.WeddingplanerApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = WeddingplanerApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
