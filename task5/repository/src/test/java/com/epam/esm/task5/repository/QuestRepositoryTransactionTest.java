package com.epam.esm.task5.repository;

import com.epam.esm.task5.repository.entity.Quest;
import com.epam.esm.task5.repository.entity.Tag;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

@ContextConfiguration(classes = TestDataConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class QuestRepositoryTransactionTest extends AbstractTransactionalJUnit4SpringContextTests {

  @Autowired private QuestRepository questRepository;

  private JdbcTemplate template;

  @Autowired
  @Override
  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Autowired
  public void setQuestRepository(QuestRepository questRepository) {
    this.questRepository = questRepository;
  }

  @Test
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  public void saveQuestWithTags_TagNameDuplicationException_transactionRollback() {
    template.execute("INSERT INTO tag (t_name) values ('sport')");
    Quest questWithExistingTagName = getQuestWithTags();
    int expectedRowsInTags = 1;
    int expectedRowsInQuest = 0;
    int expectedRowsInTagHistory = 0;
    TestTransaction.flagForCommit();
    TestTransaction.end();

    try {
      questRepository.save(questWithExistingTagName);
    } catch (Exception exception) {
    }

    Assert.assertEquals(expectedRowsInQuest, JdbcTestUtils.countRowsInTable(template, "quest"));
    Assert.assertEquals(expectedRowsInTags, JdbcTestUtils.countRowsInTable(template, "tag"));
    Assert.assertEquals(
        expectedRowsInTagHistory, JdbcTestUtils.countRowsInTable(template, "tag_history"));
  }

  @Test
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  public void saveQuestWithTags_successfulScenario_transactionCommit() {
    Quest quest = getQuestWithTags();
    int expectedRowsInTags = 1;
    int expectedRowsInQuest = 1;
    int expectedRowsInTagHistory = 1;
    TestTransaction.flagForCommit();
    TestTransaction.end();

    try {
      questRepository.save(quest);
    } catch (Exception exception) {
    }

    Assert.assertEquals(expectedRowsInQuest, JdbcTestUtils.countRowsInTable(template, "quest"));
    Assert.assertEquals(expectedRowsInTags, JdbcTestUtils.countRowsInTable(template, "tag"));
    Assert.assertEquals(
        expectedRowsInTagHistory, JdbcTestUtils.countRowsInTable(template, "tag_history"));
  }

  private Quest getQuestWithTags() {
    Quest quest = new Quest();
    quest.setName("sport_quest");
    Set<Tag> tags = new HashSet<>();
    Tag tag = new Tag();
    tag.setName("sport");
    tags.add(tag);
    quest.setTags(tags);
    return quest;
  }
}
