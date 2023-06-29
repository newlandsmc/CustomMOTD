# CustomMOTD

___
Plugin designed for [SemiVanilla-MC](https://github.com/SemiVanilla-MC/SemiVanilla-MC).
This plugin allows staff to easily manage and change MOTD that are displayed in a users' server list.

## **Downloads**
Downloads can be obtained on the [github actions page.](https://github.com/SemiVanilla-MC/CustomMOTD/actions)

## **Building**

#### Initial setup
Clone the repo using `git clone https://github.com/SemiVanilla-MC/CustomMOTD.git`.

#### Compiling
Use the command `./gradlew build --stacktrace` in the project root directory.
The compiled jar will be placed in directory `/build/libs/`.

## **Commands**

| Command     | Description                             | Permission          |
|-------------|-----------------------------------------|---------------------|
| custommotd  | Base command for the custommotd plugin. | custommotd.command  |

## **Permissions**

| Permission         | Description                                              |
|--------------------|----------------------------------------------------------|
| custommotd.command | Base permission for the custommotd command.              |
| custommotd.apply   | Allows the user to apply a custommotd.                   |
| custommotd.list    | Allows the user to list all custommotd currently loaded. |
| custommotd.counter | Allows to modify the motd counter.                       |
| custommotd.create  | Allows creating new motd using the in game tool.         |
| custommotd.reload  | Allows reloading plugin configuration.                   |
| custommotd.reset   | Allows resetting all motd to default settings.           |

## **Configuration**

```yaml
# Magic value used to determine auto configuration updates, do not change this value
config-version: 2

counter:
  # Enable counters in motd
  enabled: true
  # What counter motd should be active if multiple counter motd are active
  counter-motd: multiplenonzeromcmmo
messages:
  motd:
    set-motd: Custom MOTD has been set to <motd>
    not-a-motd: There is no motd by this name.
    set-motd-restricted: You can't set this motd.
    set-motd-default: You set the motd to default.
    set-motd-default-failed: Current motd is already default.
    counter-increased: Counter increased for <motd>
    counter-decreased: Counter decreased for <motd>
    reset: All triggers that cause a custom motd to activate have been reset.
    current-motd: Custom MOTD is <motdname>
  command:
    command-usage: Invalid usage please do /custommotd help for more info
    command-help: Custommotd available commands.
    command-help-header: =====================================
    command-help-footer: =====================================
    no-permission: You don't have permission to run this command.
    command-help-list: '<command>: <usage> - <description>'
    command-list: 'Available motd: <list>'
    command-check: The current active motd is <motd>.
```

CustomMOTD are saved within the motd directory in the CustomMOTD datafolder, where the filename is the motdname.
```yaml
#Filename test.yml

# The first line to show in the server list
line1: this is a test.
# The second line in the server list
line2: this is line2 for the test.
# Should this motd be displayed in the serverlist? Only 1 motd can be active at the same time.
active: false
# Should this motd be restricted and be controlled by a counter.
restricted: false
# counter for this mod, do not modify as the plugin needs full control of this value.
counter: 0
```

## **Support**

## **License**
[LICENSE](LICENSE)