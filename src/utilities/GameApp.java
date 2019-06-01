package utilities;

import game.arena.ArenaFactory;
import game.arena.IArena;
import game.arena.SummerArena;
import game.arena.WinterArena;
import game.competition.*;
import game.entities.sportsman.*;
import game.enums.*;
import game.states.Active;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Emilia Zorin
 * @ID 313577009
 * Assignment 4 java.
 */
@SuppressWarnings("all")
public class GameApp extends JFrame implements Observer {
    private IArena arena;
    private Competition competition_1;
    public static int currentCompetitorsAmount = 0;
    private BufferedImage backgroundImage;
    private BufferedImage competitorImage;
    private Color newColor;
    private final int size=30;
    private Observer holder = this;
    public long timeKeeperMilli;
    public static long timeKeeperSec;

    /**
     * this panel is in charge of the background image and the competitor`s images.
     */
    private JPanel panelBackground = new JPanel(){
        /**
         * this function is in charge of adding the arena image according to the user`s choice.
         * @param g
         */
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            if(getBackgroundImage()!=null)
                g.drawImage(getBackgroundImage(),0,0,this.getWidth(),this.getHeight(),null);
        }

        /**
         * this function is in charge of adding the competitor image according to competition`s and gender`s choice.
         * @param g
         */
        @Override
        public synchronized void paint(Graphics g) {
            super.paint(g);
            int i = 0;
            if (getCompetition_1() != null && getCompetition_1().hasActiveCompetitors()) {
                for (Competitor competitor : getCompetition_1().getActiveCompetitors()) {
                    double x = competitor.getThisLocation().getX();
                    g.setColor(((WinterSportsman)competitor).getColor());
                    g.drawOval((i+1)*2*size, (int)((WinterSportsman) competitor).getLocation().getX(),
                            size,size);
                    g.fillOval((i+1)*2*size, (int)((WinterSportsman) competitor).getLocation().getX(),
                            size,size);
                    g.drawImage(getCompetitorImage(), (i+1)*2*size, (int) x, 50, 50, null);
                    i++;
                }
            }
        }
    };

    /**
     * Constructor - the main container and the two panels are built here.
     * one panel is for the background image-therefore panelBackground variable. the other panel is for setting the
     * race-therefore the panelCommand variable.
     * the information table is also made here.
     * @param Title
     */
    public GameApp(String Title) {
        super(Title);
        this.setSize(1000, 1000);
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //main container
        Container mainContainer = this.getContentPane();
        //region PANEL
        JPanel panelCommand = new JPanel();
        //setting the panels
        panelCommand.setLayout(new GridLayout(45, 1, 1, 1));
        panelCommand.setBorder(new LineBorder(Color.DARK_GRAY, 1));
        //adding panels to main container
        mainContainer.add(panelCommand, BorderLayout.EAST);
        mainContainer.add(panelBackground);
        //endregion
        //region Labels
        //BUILD ARENA panel
        JLabel labelBuildArena = new JLabel("BUILD ARENA");
        labelBuildArena.setForeground(Color.blue);
        //underline for labelBuildArena
        Font font_BA = labelBuildArena.getFont();
        Map attributes_BA = font_BA.getAttributes();
        attributes_BA.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        labelBuildArena.setFont(font_BA.deriveFont(attributes_BA));
        JLabel labelArenaLength = new JLabel("Arena Length");
        JLabel labelSnowSurface = new JLabel("Snow Surface");
        JLabel labelWeatherCon = new JLabel("Weather Conditions");
        //CREATE COMPETITION panel
        JLabel labelCreateComp = new JLabel("CREATE COMPETITION");
        labelCreateComp.setForeground(Color.blue);
        //underline for labelCreateComp
        Font font_CC = labelCreateComp.getFont();
        Map attributes_CC = font_CC.getAttributes();
        attributes_CC.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        labelCreateComp.setFont(font_CC.deriveFont(attributes_CC));
        JLabel labelChooseComp = new JLabel("Choose competition");
        JLabel labelMaxComp = new JLabel("Max Competitors Number");
        JLabel labelDiscipline = new JLabel("Discipline");
        JLabel labelLeague = new JLabel("League");
        JLabel labelGender = new JLabel("Gender");
        //ADD COMPETITOR panel
        JLabel labelAddCompt = new JLabel("ADD COMPETITOR");
        labelAddCompt.setForeground(Color.blue);
        //underline for labelCreateComp
        Font font_AC = labelBuildArena.getFont();
        Map attributes_AC = font_AC.getAttributes();
        attributes_AC.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        labelAddCompt.setFont(font_AC.deriveFont(attributes_AC));
        JLabel labelName = new JLabel("Name");
        JLabel labelAge = new JLabel("Age");
        JLabel labelMaxSpeed = new JLabel("Max Speed");
        JLabel labelAcceleration = new JLabel("Acceleration");
        JLabel labelID = new JLabel("ID");
        JLabel labelArenaType = new JLabel("Arena Type");
        //endregion
        //region TextField
        final JTextField textArenaLength = new JTextField("700");
        final JTextField textMaxComp = new JTextField("10");
        final JTextField textName = new JTextField();
        final JTextField textAge = new JTextField();
        final JTextField textMaxSpeed = new JTextField();
        final JTextField textAcceleration = new JTextField();
        final JTextField textID = new JTextField();
        //endregion
        //region Buttons
        JButton buttonBuildArena = new JButton("Build Arena");
        JButton buttonSetComp = new JButton("Create Competition");
        JButton buttonAddCompt = new JButton("Add Competitor");
        JButton buttonStartComp = new JButton("Start Competition");
        JButton buttonShowInfo = new JButton("Show Info");
        JButton buttonCloneCompetitor = new JButton("Clone Competitor");
        JButton buttonChooseColor = new JButton("Choose Color");
        JButton buttonDefaultSkiCompetition = new JButton("Build default Ski competition");
        //endregion
        //region comboBox
        JComboBox comboBoxSnowSurface = new JComboBox();
        JComboBox comboBoxWeatherCon = new JComboBox();
        JComboBox comboBoxComp = new JComboBox();
        JComboBox comboBoxDiscipline = new JComboBox();
        JComboBox comboBoxLeague = new JComboBox();
        JComboBox comboBoxGender = new JComboBox();
        JComboBox comboBoxArena = new JComboBox();
        //setting comboBox
        comboBoxSnowSurface.setModel(new DefaultComboBoxModel(SnowSurface.values()));
        comboBoxWeatherCon.setModel(new DefaultComboBoxModel(WeatherCondition.values()));
        comboBoxDiscipline.setModel(new DefaultComboBoxModel(Discipline.values()));
        comboBoxLeague.setModel(new DefaultComboBoxModel(League.values()));
        comboBoxGender.setModel(new DefaultComboBoxModel(Gender.values()));
        comboBoxComp.addItem(SkiCompetition.class.getSimpleName());
        comboBoxComp.addItem(SnowboarderCompetition.class.getSimpleName());
        comboBoxArena.addItem(SummerArena.class.getSimpleName());
        comboBoxArena.addItem(WinterArena.class.getSimpleName());
        //endregion
        //region SET ARENA button
        /**
         * this function is responsible of setting the arena. when arena is set a window will pop and show
         * the information about the arena.
         * in case the user enters a wrong input a window will pop and notify him.
         */
        buttonBuildArena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ValidationUtils.assertNotNullOrEmptyString(textArenaLength.getText());
                    double arenaLen = Double.parseDouble(textArenaLength.getText());
                    ValidationUtils.assertInRange(arenaLen, 700, 900);
                    SnowSurface surface = getSnowSurface(comboBoxSnowSurface.getSelectedItem().toString());
                    WeatherCondition weatherCon = getWeatherCon(comboBoxWeatherCon.getSelectedItem().toString());
                    //set ARENA
                    String arenaType = comboBoxArena.getSelectedItem().toString();
                    ArenaFactory arenaFactory = new ArenaFactory();
                    setArena_1(arenaFactory.getArena(arenaType, arenaLen, surface, weatherCon));
                    setBackgroundImage(weatherCon);
                    panelBackground.revalidate();
                    panelBackground.repaint();
                    JOptionPane.showMessageDialog(null, arenaType + " is set: " +
                                    "\nweather: " + weatherCon + "\nlength: " + arenaLen,
                            "Information", 1);
                } catch (NullPointerException | IllegalArgumentException | IOException s) {
                    JOptionPane.showMessageDialog(null, "Invalid input values.please try again", "ERROR", 0);
                } catch (NoSuchMethodException | SecurityException g) {
                    System.out.println(g);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException c) {
                    System.out.println(c);
                }
            }
        });
        //endregion
        //region CREATE COMPETITION button
        /**
         * this function is responsible of setting the competition. when competition is set a window will pop and show
         * the information about the arena.
         * in case the user enters a wrong input a window will pop and notify him.
         */
        buttonSetComp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ValidationUtils.assertNotNullOrEmptyString(textArenaLength.getText());
                    //set COMPETITION
                    //set MAX COMPETITORS
                    int maxComp = Integer.parseInt(textMaxComp.getText());
                    ValidationUtils.assertInRange(maxComp, 1, 20);
                    //set DISCIPLINE
                    Discipline discipline = getDiscipline(comboBoxDiscipline.getSelectedItem().toString());
                    //set LEAGUE
                    League league = getLeague(comboBoxLeague.getSelectedItem().toString());
                    //set GENDER
                    Gender gender = getGender(comboBoxGender.getSelectedItem().toString());
                    //get the name of the competition
                    String nameComp = (String)(comboBoxComp.getSelectedItem());
                    //create competition dynamic class loading
                    Class c = Class.forName("game.competition." + nameComp);
                    Constructor constructor = c.getConstructor(new Class[]{IArena.class, int.class, Discipline.class, League.class, Gender.class});
                    JOptionPane.showMessageDialog(null, nameComp + " is set:" +
                                    "\nMaximum Competitors: " + maxComp + " \ndiscipline: " + discipline +
                            "\nleague: " + league + "\ngender: " + gender ,
                            "Information", 1);
                    setCompetition_1((Competition) constructor.newInstance(getArena_1(), maxComp, discipline, league, gender));
                } catch (NullPointerException | IllegalArgumentException s) {
                    JOptionPane.showMessageDialog(null, "Invalid input values.please try again", "ERROR", 0);
                }catch (NoSuchMethodException | SecurityException g){
                    System.out.println(g);
                }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException c){
                    System.out.println(c);
                }
            }
        });
        //endregion
        //region ADD COMPETITOR button
        /**
         * this function is responsible of setting the competitor. when a competitor is set a window will pop and show
         * the information about the arena.
         * in case the user enters a wrong input a window will pop and notify him.
         * also every competitor is registering an observer(the competition).
         */
        buttonAddCompt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ValidationUtils.assertNotNullOrEmptyString(textArenaLength.getText());
                    //set COMPETITOR
                    //name competition
                    String nameCompetition = (String)(comboBoxComp.getSelectedItem());
                    //set NAME
                    String nameCompetitor = textName.getText();
                    //set AGE
                    double ageCompetitor = Double.parseDouble(textAge.getText());
                    boolean ageFlag = ((WinterCompetition) competition_1).getLeague().isInLeague(ageCompetitor);
                    //set MAX SPEED
                    double maxSpeedCompetitor = Double.parseDouble(textMaxSpeed.getText());
                    //set ACCELERATION
                    double accelerationCompetitor = Double.parseDouble(textAcceleration.getText());
                    //get GENDER
                    Gender genderCompetitor = ((WinterCompetition) competition_1).getGender();
                    //get DISCIPLINE
                    Discipline disciplineCompetitor = ((WinterCompetition) competition_1).getDiscipline();
                    //set ID
                    int IDCOmpetitor = Integer.parseInt(textID.getText());
                    //competition type
                    String competitionType = null;
                    //create the competitor
                    if (competition_1.getClass().getSimpleName().equals(SkiCompetition.class.getSimpleName())) {
                        competitionType = "Skier";
                        Class c = Class.forName("game.entities.sportsman." + competitionType);
                        Constructor constructor = c.getConstructor(new Class[]{String.class, double.class, Gender.class, double.class, double.class, Discipline.class,Color.class,int.class});
                        getCompetition_1().addCompetitor((Competitor) constructor.newInstance(nameCompetitor, ageCompetitor, genderCompetitor, accelerationCompetitor, maxSpeedCompetitor, disciplineCompetitor,newColor,IDCOmpetitor));
                        Skier tempRacer = new Skier(nameCompetitor,ageCompetitor,genderCompetitor,accelerationCompetitor,maxSpeedCompetitor,disciplineCompetitor,newColor,IDCOmpetitor);
                        tempRacer.setArenaStatic(getArena_1());
                        tempRacer.registerObserver(getCompetition_1());
                        tempRacer.setState(new Active());
                    }
                    else if (competition_1.getClass().getSimpleName().equals(SnowboarderCompetition.class.getSimpleName())) {
                        competitionType = "Snowboarder";
                        Class c = Class.forName("game.entities.sportsman." + competitionType);
                        Constructor constructor = c.getConstructor(new Class[]{String.class, double.class, Gender.class, double.class, double.class, Discipline.class,Color.class,int.class});
                        getCompetition_1().addCompetitor((Competitor) constructor.newInstance(nameCompetitor, ageCompetitor, genderCompetitor, accelerationCompetitor, maxSpeedCompetitor, disciplineCompetitor,newColor,IDCOmpetitor));
                        Snowboarder tempRacer = new Snowboarder(nameCompetitor,ageCompetitor,genderCompetitor,accelerationCompetitor,maxSpeedCompetitor,disciplineCompetitor,newColor,IDCOmpetitor);
                        tempRacer.setArenaStatic(getArena_1());
                        tempRacer.registerObserver(getCompetition_1());
                        tempRacer.setState(new Active());
                    }
                    if(!ageFlag){
                        JOptionPane.showMessageDialog(null, "Competitor" +
                                "does not fit competition! Choose another competitor", "ERROR", 0);
                    }
                    getCompetition_1().setArena(getArena_1());
                    currentCompetitorsAmount+=1;
                    //Adding FEMALE/MALE icon
                    setCompetitorImage(genderCompetitor,nameCompetition);
                    panelBackground.revalidate();
                    panelBackground.repaint();
                    JOptionPane.showMessageDialog(null, "Competitor is added: " +
                            "\nname: " + nameCompetitor + " \nage: " + ageCompetitor + " \nmaximum speed: " + maxSpeedCompetitor +
                            " \nacceleration: " + accelerationCompetitor + "\nID: " + IDCOmpetitor,
                            "Information", 1);
                } catch (NullPointerException | IllegalArgumentException s) {
                    JOptionPane.showMessageDialog(null, "Invalid input values.please try again", "ERROR", 0);
                } catch (NoSuchMethodException | SecurityException g) {
                    System.out.println(g);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException c) {
                    System.out.println(c);
                }catch (Exception ignore) {
                }

            }
        });
        //endregion
        //region START COMPETITION button
        /**
         * this function is responsible of starting the competition. a thread for the competition is made
         * and he operates the game. a thread for each competitor is also made in the 'playTurn' function that is
         * being called in the run function.
         */
        buttonStartComp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ValidationUtils.assertNotNull(arena);
                    ValidationUtils.assertNotNull(competition_1);
                    ValidationUtils.assertNotNull(competition_1.getActiveCompetitors());
                } catch (IllegalArgumentException | NullPointerException s) {
                    JOptionPane.showMessageDialog(null, "Please build arena," +
                            "create competition and add competitors", "ERROR", 0);
                }
                for(int i=0;i<getCompetition_1().getActiveCompetitors().size();i++) {
                    ((WinterSportsman)getCompetition_1().getActiveCompetitors().get(i)).addObserver(holder);
                }
                getCompetition_1().playTurn();
                timeKeeperMilli = System.currentTimeMillis();
                timeKeeperSec = milliToSec(timeKeeperMilli);
            }
        });
        //endregion
        //region SHOW INFO button
        /**
         * this function is responsible for showing the info about the competitors in real time.
         */
        buttonShowInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ValidationUtils.assertNotNull(arena);
                    ValidationUtils.assertNotNull(competition_1);
                    ValidationUtils.assertNotNull(competition_1.getActiveCompetitors());
                    //Setting the JTable
                    JFrame frameTable = new JFrame();
                    JTable tableInfo;
                    frameTable.setTitle("Competitors Information");
                    //column names
                    String[] columnsNames = {"Name", "Speed", "Max Speed", "Location","State", "Finished","Time" };
                    //Initializing the JTable
                    tableInfo = new JTable(getCompetitorsInfo(), columnsNames);
                    tableInfo.setBounds(30, 40, 200, 300);
                    JScrollPane sp = new JScrollPane(tableInfo);
                    frameTable.add(sp);
                    frameTable.setSize(500, 200);
                    frameTable.setVisible(true);
                }catch (IllegalArgumentException | NullPointerException s) {
                    JOptionPane.showMessageDialog(null, "Please build arena," +
                            "create competition and add competitors", "ERROR", 0);
                }
            }
        });
        //endregion
        //region CLONE COMPETITOR
        /**
         * clone competitor
         */
        buttonCloneCompetitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnsNames = {"Number","Name", "Speed", "Acceleration", "Age", "Gender", "Discipline", "Color", "ID"};
                JTable table = new JTable(getCompetitorsInfoToClone(), columnsNames);;
                JTextField competitorNumber = new JTextField(5);
                JPanel panel = new JPanel();
                JButton pickCompetitor = new JButton("clone competitor");
                panel.add(competitorNumber);
                panel.add(pickCompetitor);

                JFrame frame = new JFrame("Choose Competitor To Clone");
                frame.add(panel,BorderLayout.SOUTH);
                frame.add(new JScrollPane(table),BorderLayout.NORTH);
                frame.pack();
                frame.setVisible(true);
                pickCompetitor.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int index = Integer.parseInt(competitorNumber.getText());
                        Competitor changeable = getCompetition_1().getActiveCompetitors().get(index-1);
                        Competitor toChange = (Competitor)changeable.clone();
                        JLabel labelID = new JLabel("ID");
                        JLabel labelColor = new JLabel("color");
                        JButton ok = new JButton("OK");
                        JTextField ID = new JTextField(10);
                        JComboBox color = new JComboBox();
                        color.setModel(new DefaultComboBoxModel(Colors.values()));
                        JPanel panel2 = new JPanel();
                        panel2.add(labelID);
                        panel2.add(ID);
                        panel2.add(labelColor);
                        panel2.add(color);
                        panel2.add(ok);

                        JFrame frame2 = new JFrame("Choose Color and ID");
                        frame2.add(panel2);
                        frame2.pack();
                        frame2.setVisible(true);
                        frame.dispose();
                        ok.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int newID = Integer.parseInt(ID.getText());
                                Color newColor = StringToColor(color.getSelectedItem().toString());
                                ((WinterSportsman)toChange).setColor(newColor);
                                ((WinterSportsman)toChange).setID(newID);
                                getCompetition_1().addCompetitor((Competitor)toChange);
                                JOptionPane.showMessageDialog(null, "Competitor has beed cloned and updated: " +
                                                "\nname: " + ((WinterSportsman)changeable).getName() + "\nage: " + ((WinterSportsman)changeable).getAge() +
                                                "\nmaximum speed: " + ((WinterSportsman)changeable).getMaxSpeed() +
                                                "\nacceleration : " + ((WinterSportsman)changeable).getAcceleration()
                                                 + " \nnew color: " + color.getSelectedItem().toString(),
                                        "Information", 1);
                                currentCompetitorsAmount+=1;
                                panelBackground.revalidate();
                                panelBackground.repaint();
                                frame2.dispose();
                            }
                        });
                    }
                });
            }
        });
        //endregion
        //region COMPETITOR COLOR
        /**
         * choose color for competitor
         */
        buttonChooseColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame = new JFrame("JColorChooser Demo");
                newColor = JColorChooser.showDialog(frame, "Choose Competitor`s Color", frame.getBackground());
                if (newColor != null) {
                    frame.getContentPane().setBackground(newColor);
                }
            }
        });
        //endregion
        //region DEFAULT SKI COMPETITION
        /**
         * building default competiton.
         */
        buttonDefaultSkiCompetition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                String result = JOptionPane.showInputDialog(frame, "Enter competitors amount to be in" +
                        " the ski competition:");
                int amount = Integer.parseInt(result);
                try {
                    setBackgroundImage(WeatherCondition.SUNNY);
                    SkiCompetitionBuilder builder = new SkiCompetitionBuilder(amount);
                    SkiCompetitionEngineer engineer = new SkiCompetitionEngineer(builder);
                    engineer.constructSkiCompetition();
                    Competition defaultCompetition = engineer.getDefCompetition();
                    setCompetition_1(defaultCompetition);
                    setArena_1(defaultCompetition.getArena());
                    setDefaultCompetitorImage();
                    panelBackground.revalidate();
                    panelBackground.repaint();
                    decorateCompetitor();
                } catch (IOException i) {
                    System.out.println(i);
                }
            }
        });
        //endregion
        //region add panelCommend
        //Arena
        panelCommand.add(labelBuildArena);
        panelCommand.add(labelArenaType);
        panelCommand.add(comboBoxArena);
        panelCommand.add(labelArenaLength);
        panelCommand.add(textArenaLength);
        panelCommand.add(labelSnowSurface);
        panelCommand.add(comboBoxSnowSurface);
        panelCommand.add(labelWeatherCon);
        panelCommand.add(comboBoxWeatherCon);
        panelCommand.add(buttonBuildArena);
        panelCommand.add(Box.createVerticalStrut(1));
        panelCommand.add(new JSeparator(SwingConstants.HORIZONTAL));
        //Competition
        panelCommand.add(labelCreateComp);
        panelCommand.add(labelChooseComp);
        panelCommand.add(comboBoxComp);
        panelCommand.add(labelMaxComp);
        panelCommand.add(textMaxComp);
        panelCommand.add(labelDiscipline);
        panelCommand.add(comboBoxDiscipline);
        panelCommand.add(labelLeague);
        panelCommand.add(comboBoxLeague);
        panelCommand.add(labelGender);
        panelCommand.add(comboBoxGender);
        panelCommand.add(buttonSetComp);
        panelCommand.add(buttonDefaultSkiCompetition);
        panelCommand.add(Box.createVerticalStrut(1));
        panelCommand.add(new JSeparator(SwingConstants.HORIZONTAL));
        //Competitor
        panelCommand.add(labelAddCompt);
        panelCommand.add(labelName);
        panelCommand.add(textName);
        panelCommand.add(labelAge);
        panelCommand.add(textAge);
        panelCommand.add(labelMaxSpeed);
        panelCommand.add(textMaxSpeed);
        panelCommand.add(labelAcceleration);
        panelCommand.add(textAcceleration);
        panelCommand.add(labelID);
        panelCommand.add(textID);
        panelCommand.add(buttonChooseColor);
        panelCommand.add(buttonAddCompt);
        panelCommand.add(buttonCloneCompetitor);
        panelCommand.add(Box.createVerticalStrut(1));
        panelCommand.add(new JSeparator(SwingConstants.HORIZONTAL));
        //Other
        panelCommand.add(buttonStartComp);
        panelCommand.add(buttonShowInfo);
        //validate();
        //endregion
    }
    //region FUNCTIONS

    /**
     * this function sets the default background image according to the user`s choice.
     * @throws IOException
     */
    public void setDefaultCompetitorImage() throws IOException{
        competitorImage = ImageIO.read(new File("SkiFemale.png"));
    }

    /**
     * this function sets the background image according to the user`s choice.
     * @param weatherCondition
     * @throws IOException
     */
    public void setBackgroundImage(WeatherCondition weatherCondition)throws IOException {
        if(weatherCondition.equals(WeatherCondition.CLOUDY))
            backgroundImage = ImageIO.read(new File("Cloudy.jpg"));
        else if(weatherCondition.equals(WeatherCondition.STORMY))
            backgroundImage = ImageIO.read(new File("Stormy.jpg"));
        else
            backgroundImage = ImageIO.read(new File("Sunny.jpg"));
    }

    /**
     * this function sets the competitor`s image according to the user`s and competition`s choice.
     * @param gender
     * @param competitionType
     * @throws IOException
     */
    public void setCompetitorImage(Gender gender, String competitionType)throws IOException {
        if(gender.equals(Gender.FEMALE)) {
            if (competitionType.equals(SkiCompetition.class.getSimpleName()))
                competitorImage = ImageIO.read(new File("SkiFemale.png"));
            else
                competitorImage = ImageIO.read(new File("SnowboardFemale.png"));
        }else if(gender.equals(Gender.MALE)){
            if (competitionType.equals(SkiCompetition.class.getSimpleName()))
                competitorImage = ImageIO.read(new File("SkiMale.png"));
            else
                competitorImage = ImageIO.read(new File("SnowboardMale.png"));
        }
    }

    /**
     * get function for competitor image
     * @return BufferedImage
     */
    public BufferedImage getCompetitorImage() {
        return competitorImage;
    }

    /**
     * get function for background image
     * @return BufferedImage
     */
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * set function for arena
     * @param arena
     */
    public void setArena_1(IArena arena) {
        this.arena = arena;
    }

    /**
     * get function for arena
     * @return arena
     */
    public IArena getArena_1() {
        return arena;
    }

    /**
     * get function for competition
     * @return competition_1
     */
    public Competition getCompetition_1() {
        return competition_1;
    }

    /**
     * set function for competition
     * @param competition
     */
    public void setCompetition_1(Competition competition) {
        this.competition_1 = competition;
    }

    /**
     * get function for SnowSurface
     * @param args
     * @return SnowSurface
     */
    public SnowSurface getSnowSurface(String args) {
        if (args == null)
            throw new NullPointerException("getSnowSurface is null");
        if (args.equals("CRUD"))
            return SnowSurface.CRUD;
        if (args.equals("ICE"))
            return SnowSurface.ICE;
        return SnowSurface.POWDER;
    }

    /**
     * get function for WeatherCondition
     * @param args
     * @return WeatherCondition
     */
    public WeatherCondition getWeatherCon(String args) {
        if (args == null)
            throw new NullPointerException("getWeatherCondition is null");
        if (args.equals("SUNNY"))
            return WeatherCondition.SUNNY;
        if (args.equals("CLOUDY"))
            return WeatherCondition.CLOUDY;
        return WeatherCondition.STORMY;
    }

    /**
     * get function for Discipline
     * @param args
     * @return Discipline
     */
    public Discipline getDiscipline(String args) {
        if (args == null)
            throw new NullPointerException("getDisciplineCondition is null");
        if (args.equals("SLALOM"))
            return Discipline.SLALOM;
        if (args.equals("GIANT SLALOM"))
            return Discipline.GIANT_SLALOM;
        if (args.equals("DOWNHILL"))
            return Discipline.DOWNHILL;
        return Discipline.FREESTYLE;
    }

    /**
     * get function for League
     * @param args
     * @return League
     */
    public League getLeague(String args) {
        if (args == null)
            throw new NullPointerException("getLeague is null");
        if (args.equals("JUNIOR"))
            return League.JUNIOR;
        if (args.equals("ADULT"))
            return League.ADULT;
        return League.SENIOR;
    }

    /**
     * get function for Gender
     * @param args
     * @return Gender
     */
    public Gender getGender(String args) {
        if (args == null)
            throw new NullPointerException("getGender is null");
        if (args.equals("MALE"))
            return Gender.MALE;
        return Gender.FEMALE;
    }

    /**
     * this function builds the information table about the competitors during the game.
     * @return Object[][]
     */
    public Object[][] getCompetitorsInfo() {
        Object[][] competitorsInfo = null;
        if (getCompetition_1() != null) {
            competitorsInfo = new Object[getCompetition_1().getFinishedCompetitors().size() + getCompetition_1().getActiveCompetitors().size()][7];
            int i = 0;
            for (int j = 0; j < getCompetition_1().getFinishedCompetitors().size(); j++) {
                competitorsInfo[i] = new Object[]{((WinterSportsman) getCompetition_1().getFinishedCompetitors().get(j)).getName(),
                        (((WinterSportsman) getCompetition_1().getFinishedCompetitors().get(j)).getSpeed()),
                        ((WinterSportsman) getCompetition_1().getFinishedCompetitors().get(j)).getMaxSpeed(),
                        getCompetition_1().getFinishedCompetitors().get(j).getThisLocation().getX(),
                        (((WinterSportsman) getCompetition_1().getFinishedCompetitors().get(j)).getState().inform()),
                        "Yes", timeKeeperSec
                };
                i++;
            }
            i = 0;
            for (int j = 0; j < getCompetition_1().getActiveCompetitors().size(); j++) {
                competitorsInfo[i] = new Object[]{((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getName(),
                        (((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getSpeed()),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getMaxSpeed(),
                        getCompetition_1().getActiveCompetitors().get(j).getThisLocation().getX(),
                        (((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getState().inform()),
                        "No" , timeKeeperSec
                };
                i++;
            }
        }
        return competitorsInfo;
    }

    /**
     * this function builds the information table about the default competitors.
     * @return Object[][]
     */
    public Object[][] getCompetitorsInfoToClone() {
        Object[][] competitorsInfoToClone = null;
        if (getCompetition_1() != null) {
            int i;
            competitorsInfoToClone = new Object[getCompetition_1().getFinishedCompetitors().size() + getCompetition_1().getActiveCompetitors().size()][9];
            i = 0;
            for (int j = 0; j < getCompetition_1().getActiveCompetitors().size(); j++) {
                competitorsInfoToClone[i] = new Object[]{(j+1),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getName(),
                        (((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getSpeed()),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getAcceleration(),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getAge(),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getGender(),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getDiscipline(),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getNameColor(),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getID()
                };
                i++;
            }
        }
        return competitorsInfoToClone;
    }

    /**
     * converting string to color
     * @param color
     * @return Color
     */
    public Color StringToColor(String color){
        if(color.equals("BLACK"))
            return Color.BLACK;
        else if(color.equals("RED"))
            return Color.RED;
        else if(color.equals("GREEN"))
            return Color.GREEN;
        else if(color.equals("BLUE"))
            return Color.BLUE;
        else if(color.equals("PINK"))
            return Color.PINK;
        else if(color.equals("YELLOW"))
            return Color.YELLOW;
        else if(color.equals("GRAY"))
            return Color.GRAY;
        return Color.WHITE;
    }

    /**
     * showing the user the current competitors in the cometition so that he can pick one and decorate him.
     * @return Object[][]
     */
    public Object[][] decorateCompetitor() {
        Object[][] Info = null;
        if (getCompetition_1() != null) {
            int i;
            Info = new Object[getCompetition_1().getFinishedCompetitors().size() + getCompetition_1().getActiveCompetitors().size()][9];
            i = 0;
            for (int j = 0; j < getCompetition_1().getActiveCompetitors().size(); j++) {
                Info[i] = new Object[]{(j + 1),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getName(),
                        (((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getSpeed()),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getAcceleration(),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getAge(),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getGender(),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getDiscipline(),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getNameColor(),
                        ((WinterSportsman) getCompetition_1().getActiveCompetitors().get(j)).getID()
                };
                i++;
            }
        }
        String[] columnsNames = {"Number","Name", "Speed", "Acceleration", "Age", "Gender", "Discipline", "Color", "ID"};
        JTable table = new JTable(Info, columnsNames);;
        JTextField competitorNumber = new JTextField(5);
        JPanel panel = new JPanel();
        JButton decorateCompetitor = new JButton("decorate competitor");
        panel.add(competitorNumber);
        panel.add(decorateCompetitor);

        JFrame frame = new JFrame("information");
        frame.add(panel,BorderLayout.SOUTH);
        frame.add(new JScrollPane(table),BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
        decorateCompetitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = Integer.parseInt(competitorNumber.getText());
                Competitor toDecorate  = getCompetition_1().getActiveCompetitors().get(index-1);
                JLabel labelAcc = new JLabel("Acceleration");
                JLabel labelColor = new JLabel("color");
                JButton ok = new JButton("OK");
                JTextField ID = new JTextField(10);
                JComboBox color = new JComboBox();
                color.setModel(new DefaultComboBoxModel(Colors.values()));
                JPanel panel2 = new JPanel();
                panel2.add(labelAcc);
                panel2.add(ID);
                panel2.add(labelColor);
                panel2.add(color);
                panel2.add(ok);
                JFrame frame2 = new JFrame("Choose Color and Acceleration");
                frame2.add(panel2);
                frame2.pack();
                frame2.setVisible(true);
                frame.dispose();
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        double newAcc = Double.parseDouble(ID.getText());
                        Color newColor = StringToColor(color.getSelectedItem().toString());
                        ColoredSportsman coloredSportsman = new ColoredSportsman((IWinterSportsman)toDecorate);
                        coloredSportsman.setColor(newColor);
                        SpeedySportsman speedySportsman = new SpeedySportsman((IWinterSportsman)toDecorate);
                        speedySportsman.setAcceleration(newAcc);
                        panelBackground.revalidate();
                        panelBackground.repaint();
                        frame2.dispose();

                    }
                });
            }
        });
        return Info;
    }

    /**
     * updating the panel background frame during the game.
     * @param o
     * @param arg
     */
    @Override
    public synchronized void update(Observable o, Object arg) {
        if (getCompetition_1().hasActiveCompetitors()) {
            panelBackground.revalidate();
            panelBackground.repaint();
        }
    }

    /**
     * converting milliseconds to seconds
     * @param t
     * @return
     */
    public long milliToSec(long t){
        return t/1000;
    }
    //endregion
}


