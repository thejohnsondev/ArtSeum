//
//  SearchItemView.swift
//  iosApp
//
//  Created by Andrew on 28.01.2026.
//

import SwiftUI
import Shared

struct SearchItemView: View {
    let item: ArtworkSearchItem
    
    private var lqipImage: UIImage? {
        item.thumbnail?.lqip.base64ToImage()
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 16) {
            Group {
                if let uiImage = lqipImage {
                    Image(uiImage: uiImage)
                        .resizable()
                        .scaledToFill()
                } else {
                    Color.gray.opacity(0.3)
                        .overlay {
                            Image(systemName: "photo")
                                .resizable()
                                .padding(2)
                                .foregroundColor(.gray)
                        }
                }
            }
            .frame(width: 16, height: 16)
            .clipShape(RoundedRectangle(cornerRadius: 4))
            
            VStack(alignment: .leading, spacing: 4) {
                Text(item.title ?? "Untitled")
                    .font(.headline)
                    .foregroundColor(.white)
                    .lineLimit(2)
                
                Text(item.thumbnail?.altText ?? "")
                    .font(.subheadline)
                    .foregroundColor(.gray)
                    .lineLimit(2)
            }
            
            Spacer()
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 8)
        .background(Color.black)
    }
}

#Preview {
    SearchItemView(item: ArtworkSearchItem.companion.demo1)
        .preferredColorScheme(.dark)
}
