//
//  Estensions.swift
//  iosApp
//
//  Created by Andrew on 26.01.2026.
//

import UIKit
import Shared

extension String {
    func base64ToImage() -> UIImage? {
        let cleanString = self.components(separatedBy: ",").last ?? self
        
        guard let data = Data(base64Encoded: cleanString, options: .ignoreUnknownCharacters) else {
            return nil
        }
        return UIImage(data: data)
    }
}

extension DisplayableMessageValue {
    var message: String {
        if self is DisplayableMessageValue.NoInternetError {
            return "No internet connection. Please check your network and try again."
        } else if self is DisplayableMessageValue.UnknownError {
            return "Something went wrong. Please try again later."
        } else if let stringValue = self as? DisplayableMessageValue.StringValue {
            return stringValue.value
        }
        return "Unknown error"
    }
}
