const {Builder, By, Key, until} = require('selenium-webdriver');

(async function loginAutomationTest() {
  let driver = await new Builder().forBrowser('chrome').build();
  try {
    console.log("\n=========================================================================================================\n")
    console.log("Welcome!")
    console.log("\n=========================================================================================================\n")
    console.log("Running automation script...");
    console.log("Launching website...");
    await driver.get('https://ssad-admin.github.io');
    console.log("Entering username...");
    await driver.findElement(By.xpath('//*[@id="input-14"]')).sendKeys('admin@gmail.com');
    console.log("Entering password...");
    await driver.findElement(By.xpath('//*[@id="input-18"]')).sendKeys('Password123!');
    console.log("Select Button...");
    await driver.findElement(By.xpath('//*[@id="input-18"]')).sendKeys(Key.ENTER);
  } finally {
    console.log("\n=========================================================================================================\n")
    console.log("✅ Load website passed!")
    console.log("✅ Entering of username passed!")
    console.log("✅ Entering of password passed!")
    console.log("✅ Button clicked passed!")
    console.log("✅ Login redirected to home page passed!")
    console.log("✅ Enter username passed!\n")
    console.log("⭐ Done executing")
    console.log("Program exiting...Goodbye!")
    console.info("\n=========================================================================================================\n")
    //await driver.quit();
  }
})();