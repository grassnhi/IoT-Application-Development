/*

Given an LED turns on for T_on and then turns off for T_off. 
Design an DFA for this LED 
Implement the DFA in Arduino 

▪ digitalWrite(13, HIGH): turn on the LED 
▪ digitalWrite(13, LOW): turn off the LED 
▪ setTime(duration): set a clock (timer_flag = 0), when the clock is expired, timer_flag = 1; 
duration is in mili-seconds.


=>> INIT: Set pin 13 to OUTPUT mode, set timer 
    LED_ON: Turn on the LED 
    LED_OFF: Turn off the LED 
*/

void loop(){ 
    switch(status){ 
        case INIT: 
            pinMode(13, OUTPUT); 
            setTimer(T_on); 
            status = LED_ON; 
            digitalWrite(13, HIGH); 
            break; 
            
        case LED_ON: 
            if(timer_flag == 1){ 
                status = LED_OFF; 
                setTimer(T_off); 
                digitalWrite(13, LOW); 
            } 
            break; 
            
        case LED_OFF: 
            if(timer_flag == 1){ 
                status = LED_ON; 
                setTimer(T_on); 
                digitalWrite(13, HIGH); 
            } 
            break; 
            
        default: 
            break; 
    } 
    
    delay(10); 
} 

void timer_run(){ 
    if(timer_counter > 0) 
        timer_counter--; 
    if(counter_timer == 0) 
        timer_flag =1; 
} 

void setTimer(long duration){ 
    timer_counter = duration; 
    timer_flag = 0; 
}
