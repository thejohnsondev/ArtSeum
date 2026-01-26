//
//  Estensions.swift
//  iosApp
//
//  Created by Andrew on 26.01.2026.
//

import UIKit

extension String {
    func base64ToImage() -> UIImage? {
        let cleanString = self.components(separatedBy: ",").last ?? self
        
        guard let data = Data(base64Encoded: cleanString, options: .ignoreUnknownCharacters) else {
            return nil
        }
        return UIImage(data: data)
    }
}
