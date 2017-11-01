Sunmi Inner Scanner Cordova Plugin 
======

Tested on V1 machine.


## Install

#### before Cordova@7.0
    cordova plugin add https://github.com/siwei0729/cordova-sunmi-inner-scanner.git

#### after Cordova@7.0
    cordova plugin add https://github.com/siwei0729/cordova-sunmi-inner-scanner.git --nofetch


## Usage

    export class YourAppPage {
    
        windowObj:any = window;
    
        constructor() {}
        
        scan(){
             this.windowObj.SunmiScanner.scan()
                        .then((barcodeData) => {
                            console.log(barcodeData);
                        }).catch((e) => {
                            console.log(e);
                    });
        }
    }